package facade;

import facade.model.BurgerType;
import facade.model.OrderConfirmation;
import facade.model.OrderRequest;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Client: orders burgers through the facade only and never touches the subsystem.
 */
public class FacadeDemo {
    private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.systemDefault());

    public static void main(String[] args) {
        System.out.println("=== FACADE PATTERN DEMO ===");
        System.out.println();

        BurgerOrderFacade burgerShop = BurgerOrderFacade.createDefault();
        demonstrateSuccessfulOrder(burgerShop);
        demonstrateFailedOrders(burgerShop);
    }

    private static void demonstrateSuccessfulOrder(BurgerOrderFacade burgerShop) {
        System.out.println("1) One call places the order; another one tracks it:");

        OrderConfirmation confirmation = burgerShop.placeOrder(
                new OrderRequest(BurgerType.SMASH, 2, "Zone A, Mangilik El 55", "tok_visa"));
        System.out.printf("   Order %s confirmed: %d x %s, paid %s, arrives at about %s%n",
                confirmation.orderId(), confirmation.quantity(), confirmation.burgerType().displayName(),
                confirmation.totalPaid(), TIME.format(confirmation.estimatedDelivery()));
        System.out.println("   Tracking " + confirmation.orderId() + ": " + burgerShop.trackOrder(confirmation.orderId()));
        System.out.println("   Tracking ORD-9999: " + burgerShop.trackOrder("ORD-9999"));
        System.out.println();
    }

    private static void demonstrateFailedOrders(BurgerOrderFacade burgerShop) {
        System.out.println("2) Every failure arrives as one OrderFailedException with a reason:");

        tryToOrder(burgerShop, new OrderRequest(BurgerType.CHICKEN, 4, "Zone B, Turan Ave 37", "tok_visa"));
        tryToOrder(burgerShop, new OrderRequest(BurgerType.SMASH, 1, "Zone A, Mangilik El 55", "tok_declined"));
        tryToOrder(burgerShop, new OrderRequest(BurgerType.CHICKEN, 1, "Zone Z, Kabanbay Batyr 60", "tok_visa"));
    }

    private static void tryToOrder(BurgerOrderFacade burgerShop, OrderRequest request) {
        try {
            burgerShop.placeOrder(request);
            System.out.println("   Unexpected success for " + request);
        } catch (OrderFailedException e) {
            System.out.println("   " + e.reason() + " -> " + e.getMessage());
        }
    }
}
