package facade;

import facade.model.DeliveryStatus;
import facade.model.FailureReason;
import facade.model.Ingredient;
import facade.model.Money;
import facade.model.OrderConfirmation;
import facade.model.OrderRequest;
import facade.subsystem.Delivery;
import facade.subsystem.DeliveryDispatcher;
import facade.subsystem.DeliveryException;
import facade.subsystem.InventoryService;
import facade.subsystem.KitchenException;
import facade.subsystem.KitchenStation;
import facade.subsystem.OutOfStockException;
import facade.subsystem.PackagingService;
import facade.subsystem.PackedOrder;
import facade.subsystem.PaymentDeclinedException;
import facade.subsystem.PaymentGateway;
import facade.subsystem.Receipt;
import facade.subsystem.Reservation;
import facade.subsystem.SimulatedPaymentGateway;
import facade.subsystem.SubsystemException;

import java.time.Clock;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * Facade: places a burger order with one call and undoes the completed steps when a later one fails.
 */
public final class BurgerOrderFacade {
    private static final int DEFAULT_KITCHEN_CAPACITY = 5;
    private static final Money DEFAULT_CHARGE_LIMIT = Money.of("50000");

    private final InventoryService inventory;
    private final PaymentGateway payments;
    private final KitchenStation kitchen;
    private final PackagingService packaging;
    private final DeliveryDispatcher deliveries;
    private final Clock clock;
    private final Supplier<String> nextOrderId;

    BurgerOrderFacade(InventoryService inventory, PaymentGateway payments, KitchenStation kitchen,
                      PackagingService packaging, DeliveryDispatcher deliveries,
                      Clock clock, Supplier<String> nextOrderId) {
        this.inventory = Objects.requireNonNull(inventory, "Inventory must not be null");
        this.payments = Objects.requireNonNull(payments, "Payment gateway must not be null");
        this.kitchen = Objects.requireNonNull(kitchen, "Kitchen must not be null");
        this.packaging = Objects.requireNonNull(packaging, "Packaging service must not be null");
        this.deliveries = Objects.requireNonNull(deliveries, "Delivery dispatcher must not be null");
        this.clock = Objects.requireNonNull(clock, "Clock must not be null");
        this.nextOrderId = Objects.requireNonNull(nextOrderId, "Order id supplier must not be null");
    }

    public static BurgerOrderFacade createDefault() {
        Clock clock = Clock.systemUTC();
        AtomicInteger orderSequence = new AtomicInteger();
        return new BurgerOrderFacade(
                seededInventory(),
                new SimulatedPaymentGateway(DEFAULT_CHARGE_LIMIT),
                new KitchenStation(DEFAULT_KITCHEN_CAPACITY),
                new PackagingService(),
                new DeliveryDispatcher(clock),
                clock,
                () -> String.format("ORD-%04d", orderSequence.incrementAndGet()));
    }

    public OrderConfirmation placeOrder(OrderRequest request) {
        Objects.requireNonNull(request, "Order request must not be null");
        String orderId = nextOrderId.get();
        Rollback rollback = new Rollback();
        try {
            return fulfil(orderId, request, rollback);
        } catch (SubsystemException failure) {
            OrderFailedException orderFailure = orderFailed(orderId, request, failure);
            rollback.undoCompletedSteps(orderFailure);
            throw orderFailure;
        } catch (RuntimeException unexpected) {
            rollback.undoCompletedSteps(unexpected);
            throw unexpected;
        }
    }

    public DeliveryStatus trackOrder(String orderId) {
        Objects.requireNonNull(orderId, "Order id must not be null");
        return deliveries.statusOf(orderId);
    }

    private OrderConfirmation fulfil(String orderId, OrderRequest request, Rollback rollback) {
        reserveIngredients(request, rollback);
        Receipt receipt = charge(request, rollback);
        PackedOrder packedOrder = packaging.pack(kitchen.cook(request.burgerType(), request.quantity()));
        Delivery delivery = deliveries.schedule(orderId, packedOrder, request.deliveryAddress());
        return new OrderConfirmation(orderId, request.burgerType(), request.quantity(),
                receipt.amount(), clock.instant(), delivery.estimatedArrival());
    }

    private void reserveIngredients(OrderRequest request, Rollback rollback) {
        Reservation reservation = inventory.reserve(request.burgerType(), request.quantity());
        rollback.register(() -> inventory.release(reservation));
    }

    private Receipt charge(OrderRequest request, Rollback rollback) {
        Receipt receipt = payments.charge(request.paymentToken(), request.totalPrice());
        rollback.register(() -> payments.refund(receipt));
        return receipt;
    }

    private static OrderFailedException orderFailed(String orderId, OrderRequest request, SubsystemException failure) {
        String message = String.format("Order %s (%d x %s to '%s') failed: %s", orderId, request.quantity(),
                request.burgerType(), request.deliveryAddress(), failure.getMessage());
        return new OrderFailedException(reasonFor(failure), message, failure);
    }

    private static FailureReason reasonFor(SubsystemException failure) {
        if (failure instanceof OutOfStockException) {
            return FailureReason.OUT_OF_STOCK;
        }
        if (failure instanceof PaymentDeclinedException) {
            return FailureReason.PAYMENT_DECLINED;
        }
        if (failure instanceof KitchenException) {
            return FailureReason.KITCHEN_UNAVAILABLE;
        }
        if (failure instanceof DeliveryException) {
            return FailureReason.DELIVERY_UNAVAILABLE;
        }
        throw new IllegalStateException("No failure reason mapped for " + failure.getClass().getName(), failure);
    }

    private static InventoryService seededInventory() {
        InventoryService inventory = new InventoryService();
        inventory.restock(Ingredient.BUN, 20);
        inventory.restock(Ingredient.BEEF_PATTY, 30);
        inventory.restock(Ingredient.CHICKEN_FILLET, 3);
        inventory.restock(Ingredient.CHEESE, 20);
        inventory.restock(Ingredient.LETTUCE, 20);
        inventory.restock(Ingredient.SAUCE, 20);
        return inventory;
    }

    /**
     * Undo actions of the completed steps, run in reverse order.
     */
    private static final class Rollback {
        private final Deque<Runnable> undoActions = new ArrayDeque<>();

        void register(Runnable undoAction) {
            undoActions.push(undoAction);
        }

        void undoCompletedSteps(Throwable failure) {
            while (!undoActions.isEmpty()) {
                try {
                    undoActions.pop().run();
                } catch (RuntimeException undoFailure) {
                    failure.addSuppressed(undoFailure);
                }
            }
        }
    }
}
