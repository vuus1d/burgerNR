package facade;

import facade.model.BurgerType;
import facade.model.OrderRequest;

/**
 * Test language: builds valid order requests, so each test states only the detail it cares about.
 */
final class OrderRequests {
    static final String VALID_TOKEN = "tok_ok";
    static final String ZONE_A_ADDRESS = "Zone A, Mangilik El 55";

    private BurgerType burgerType = BurgerType.SMASH;
    private int quantity = 1;
    private String deliveryAddress = ZONE_A_ADDRESS;
    private String paymentToken = VALID_TOKEN;

    private OrderRequests() {
    }

    static OrderRequests anOrder() {
        return new OrderRequests();
    }

    OrderRequests of(BurgerType burgerType) {
        this.burgerType = burgerType;
        return this;
    }

    OrderRequests quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    OrderRequests deliveredTo(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    OrderRequests paidWith(String paymentToken) {
        this.paymentToken = paymentToken;
        return this;
    }

    OrderRequest build() {
        return new OrderRequest(burgerType, quantity, deliveryAddress, paymentToken);
    }
}
