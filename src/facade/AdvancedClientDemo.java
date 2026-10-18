package facade;

import facade.model.BurgerType;
import facade.model.Ingredient;
import facade.model.Money;
import facade.subsystem.DeliveryDispatcher;
import facade.subsystem.Delivery;
import facade.subsystem.InventoryService;
import facade.subsystem.KitchenStation;
import facade.subsystem.PackagingService;
import facade.subsystem.PackedOrder;
import facade.subsystem.PreparedBurger;
import facade.subsystem.Receipt;
import facade.subsystem.Reservation;
import facade.subsystem.SimulatedPaymentGateway;
import facade.subsystem.SubsystemException;

import java.time.Clock;
import java.util.List;

/**
 * Advanced client: bypasses the facade on purpose. It shows that the subsystem works on its own,
 * and how much ordering and rollback code the facade saves every ordinary client.
 */
public class AdvancedClientDemo {
    private static final BurgerType BURGER = BurgerType.SMASH;
    private static final String ADDRESS = "Zone A, Mangilik El 55";

    private final InventoryService inventory = new InventoryService();
    private final SimulatedPaymentGateway payments = new SimulatedPaymentGateway(Money.of("50000"));
    private final KitchenStation kitchen = new KitchenStation(5);
    private final PackagingService packaging = new PackagingService();
    private final DeliveryDispatcher deliveries = new DeliveryDispatcher(Clock.systemUTC());

    public static void main(String[] args) {
        System.out.println("=== FACADE PATTERN: ADVANCED CLIENT WITHOUT THE FACADE ===");
        System.out.println();

        AdvancedClientDemo client = new AdvancedClientDemo();
        client.inventory.restock(Ingredient.BUN, 10);
        client.inventory.restock(Ingredient.BEEF_PATTY, 20);
        client.inventory.restock(Ingredient.CHEESE, 10);
        client.inventory.restock(Ingredient.SAUCE, 10);

        System.out.println("1) Orchestrating every step by hand:");
        client.orderByHand("MANUAL-1", 2);
        System.out.println();

        System.out.println("2) The kitchen fails, so the client must undo the earlier steps itself:");
        client.orderByHand("MANUAL-2", 7);
    }

    private void orderByHand(String orderId, int quantity) {
        Reservation reservation = inventory.reserve(BURGER, quantity);
        System.out.println("   Reserved " + reservation + ", beef patties left: " + inventory.stockOf(Ingredient.BEEF_PATTY));
        Receipt receipt = payments.charge("tok_visa", BURGER.priceFor(quantity));
        System.out.println("   Charged " + receipt.amount() + " (" + receipt.receiptId() + ")");
        try {
            List<PreparedBurger> burgers = kitchen.cook(BURGER, quantity);
            PackedOrder packedOrder = packaging.pack(burgers);
            Delivery delivery = deliveries.schedule(orderId, packedOrder, ADDRESS);
            System.out.println("   Cooked, packed in a " + packedOrder.packaging() + ", delivery status: "
                    + deliveries.statusOf(delivery.orderId()));
        } catch (SubsystemException e) {
            System.out.println("   Failed: " + e.getMessage());
            payments.refund(receipt);
            inventory.release(reservation);
            System.out.println("   Rolled back by hand: captured " + payments.totalCaptured()
                    + ", beef patties back to " + inventory.stockOf(Ingredient.BEEF_PATTY));
        }
    }
}
