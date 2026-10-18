package facade;

import facade.model.DeliveryStatus;
import facade.model.FailureReason;
import facade.model.Ingredient;
import facade.model.Money;
import facade.model.OrderConfirmation;
import facade.model.OrderRequest;
import facade.subsystem.DeliveryDispatcher;
import facade.subsystem.KitchenStation;
import facade.subsystem.PackagingService;
import facade.subsystem.PackedOrder;
import facade.subsystem.PaymentDeclinedException;
import facade.subsystem.PreparedBurger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static facade.OrderAssertions.assertMentions;
import static facade.OrderAssertions.assertOrderFailedBecause;
import static facade.OrderRequests.anOrder;
import static facade.model.BurgerType.SMASH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BurgerOrderFacadeTest {
    private static final Instant NOW = Instant.parse("2026-10-18T12:00:00Z");
    private static final Duration ZONE_A_TRAVEL_TIME = Duration.ofMinutes(20);
    private static final String ORDER_ID = "ORD-TEST-1";
    private static final int UNITS_PER_INGREDIENT = 10;
    private static final int KITCHEN_CAPACITY = 3;

    private List<String> journal;
    private JournalingInventoryService inventory;
    private FakePaymentGateway payments;
    private BurgerOrderFacade facade;

    @BeforeEach
    void setUp() {
        journal = new ArrayList<>();
        inventory = new JournalingInventoryService(journal);
        for (Ingredient ingredient : Ingredient.values()) {
            inventory.restock(ingredient, UNITS_PER_INGREDIENT);
        }
        payments = new FakePaymentGateway(journal);
        facade = facadeWith(new PackagingService());
    }

    @Test
    void placeOrder_validRequest_returnsConfirmationWithTotalAndEstimatedDelivery() {
        OrderRequest request = anOrder().of(SMASH).quantity(2).build();

        OrderConfirmation confirmation = facade.placeOrder(request);

        assertEquals(new OrderConfirmation(ORDER_ID, SMASH, 2, Money.of("4980"), NOW, NOW.plus(ZONE_A_TRAVEL_TIME)),
                confirmation);
    }

    @Test
    void placeOrder_validRequest_chargesTotalPriceOnce() {
        OrderRequest request = anOrder().of(SMASH).quantity(2).build();

        facade.placeOrder(request);

        assertEquals(List.of(Money.of("4980")), payments.chargedAmounts());
    }

    @Test
    void placeOrder_validRequest_makesOrderTrackable() {
        OrderConfirmation confirmation = facade.placeOrder(anOrder().build());

        DeliveryStatus status = facade.trackOrder(confirmation.orderId());

        assertEquals(DeliveryStatus.ON_THE_WAY, status);
    }

    @Test
    void placeOrder_outOfStock_chargesNothing() {
        OrderRequest moreSmashBurgersThanPatties = anOrder().of(SMASH).quantity(UNITS_PER_INGREDIENT).build();

        assertThrows(OrderFailedException.class, () -> facade.placeOrder(moreSmashBurgersThanPatties));

        assertEquals(List.of(), payments.chargedAmounts());
    }

    @Test
    void placeOrder_outOfStock_failsWithOutOfStockReason() {
        OrderRequest moreSmashBurgersThanPatties = anOrder().of(SMASH).quantity(UNITS_PER_INGREDIENT).build();

        assertOrderFailedBecause(FailureReason.OUT_OF_STOCK, () -> facade.placeOrder(moreSmashBurgersThanPatties));
    }

    @Test
    void placeOrder_paymentDeclined_releasesReservedIngredients() {
        payments.declineEveryCharge();

        assertThrows(OrderFailedException.class, () -> facade.placeOrder(anOrder().of(SMASH).build()));

        assertEquals(UNITS_PER_INGREDIENT, inventory.stockOf(Ingredient.BEEF_PATTY));
    }

    @Test
    void placeOrder_paymentDeclined_failsWithPaymentDeclinedReason() {
        payments.declineEveryCharge();

        assertOrderFailedBecause(FailureReason.PAYMENT_DECLINED, () -> facade.placeOrder(anOrder().build()));
    }

    @Test
    void placeOrder_kitchenFailure_refundsThenReleasesInReverseOrder() {
        OrderRequest overKitchenCapacity = anOrder().quantity(KITCHEN_CAPACITY + 1).build();

        assertThrows(OrderFailedException.class, () -> facade.placeOrder(overKitchenCapacity));

        assertEquals(List.of("reserve", "charge", "refund", "release"), journal);
    }

    @Test
    void placeOrder_kitchenFailure_failsWithKitchenUnavailableReason() {
        OrderRequest overKitchenCapacity = anOrder().quantity(KITCHEN_CAPACITY + 1).build();

        assertOrderFailedBecause(FailureReason.KITCHEN_UNAVAILABLE, () -> facade.placeOrder(overKitchenCapacity));
    }

    @Test
    void placeOrder_deliveryFailure_refundsPayment() {
        OrderRequest toUnservedZone = anOrder().deliveredTo("Zone Z, Nowhere 1").build();

        assertThrows(OrderFailedException.class, () -> facade.placeOrder(toUnservedZone));

        assertEquals(List.of(), payments.chargedAmounts());
    }

    @Test
    void placeOrder_deliveryFailure_releasesReservedIngredients() {
        OrderRequest toUnservedZone = anOrder().of(SMASH).deliveredTo("Zone Z, Nowhere 1").build();

        assertThrows(OrderFailedException.class, () -> facade.placeOrder(toUnservedZone));

        assertEquals(UNITS_PER_INGREDIENT, inventory.stockOf(Ingredient.BEEF_PATTY));
    }

    @Test
    void placeOrder_deliveryFailure_failsWithDeliveryUnavailableReason() {
        OrderRequest toUnservedZone = anOrder().deliveredTo("Zone Z, Nowhere 1").build();

        assertOrderFailedBecause(FailureReason.DELIVERY_UNAVAILABLE, () -> facade.placeOrder(toUnservedZone));
    }

    @Test
    void placeOrder_failure_keepsSubsystemExceptionAsCause() {
        payments.declineEveryCharge();

        OrderFailedException failure = assertThrows(OrderFailedException.class, () -> facade.placeOrder(anOrder().build()));

        assertInstanceOf(PaymentDeclinedException.class, failure.getCause());
    }

    @Test
    void placeOrder_failure_messageNamesOrderIdOrderAndCause() {
        OrderRequest overKitchenCapacity = anOrder().of(SMASH).quantity(KITCHEN_CAPACITY + 1).build();

        OrderFailedException failure = assertThrows(OrderFailedException.class, () -> facade.placeOrder(overKitchenCapacity));

        assertMentions(failure.getMessage(), ORDER_ID, "4 x SMASH", "capacity is 3");
    }

    @Test
    void placeOrder_refundFailsDuringRollback_isAttachedAsSuppressed() {
        payments.failEveryRefund();
        OrderRequest overKitchenCapacity = anOrder().quantity(KITCHEN_CAPACITY + 1).build();

        OrderFailedException failure = assertThrows(OrderFailedException.class, () -> facade.placeOrder(overKitchenCapacity));

        assertEquals(1, failure.getSuppressed().length);
    }

    @Test
    void placeOrder_refundFailsDuringRollback_stillReleasesReservedIngredients() {
        payments.failEveryRefund();
        OrderRequest overKitchenCapacity = anOrder().of(SMASH).quantity(KITCHEN_CAPACITY + 1).build();

        assertThrows(OrderFailedException.class, () -> facade.placeOrder(overKitchenCapacity));

        assertEquals(UNITS_PER_INGREDIENT, inventory.stockOf(Ingredient.BEEF_PATTY));
    }

    @Test
    void placeOrder_unexpectedFailure_isRethrownUnchanged() {
        IllegalStateException bug = new IllegalStateException("packaging bug");
        facade = facadeWith(packagingThatThrows(bug));

        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> facade.placeOrder(anOrder().build()));

        assertSame(bug, thrown);
    }

    @Test
    void placeOrder_unexpectedFailure_stillRefundsPayment() {
        facade = facadeWith(packagingThatThrows(new IllegalStateException("packaging bug")));

        assertThrows(IllegalStateException.class, () -> facade.placeOrder(anOrder().build()));

        assertEquals(List.of(), payments.chargedAmounts());
    }

    @Test
    void placeOrder_nullRequest_throwsNullPointerExceptionWithMessage() {
        NullPointerException failure = assertThrows(NullPointerException.class, () -> facade.placeOrder(null));

        assertEquals("Order request must not be null", failure.getMessage());
    }

    @Test
    void trackOrder_unknownOrderId_returnsUnknown() {
        DeliveryStatus status = facade.trackOrder("ORD-DOES-NOT-EXIST");

        assertEquals(DeliveryStatus.UNKNOWN, status);
    }

    private BurgerOrderFacade facadeWith(PackagingService packaging) {
        Clock fixedClock = Clock.fixed(NOW, ZoneOffset.UTC);
        return new BurgerOrderFacade(inventory, payments, new KitchenStation(KITCHEN_CAPACITY), packaging,
                new DeliveryDispatcher(fixedClock), fixedClock, () -> ORDER_ID);
    }

    private static PackagingService packagingThatThrows(RuntimeException bug) {
        return new PackagingService() {
            @Override
            public PackedOrder pack(List<PreparedBurger> burgers) {
                throw bug;
            }
        };
    }
}
