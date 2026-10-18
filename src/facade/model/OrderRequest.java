package facade.model;

import java.util.Objects;

/**
 * Domain value: what the customer wants. Validates itself, so an invalid request cannot exist.
 */
public record OrderRequest(BurgerType burgerType, int quantity, String deliveryAddress, String paymentToken) {
    public static final int MAX_QUANTITY = 10;

    public OrderRequest {
        Objects.requireNonNull(burgerType, "Burger type must not be null");
        if (quantity < 1 || quantity > MAX_QUANTITY) {
            throw new IllegalArgumentException(
                    "Quantity must be between 1 and " + MAX_QUANTITY + ", but was " + quantity);
        }
        requireNonBlank(deliveryAddress, "Delivery address");
        requireNonBlank(paymentToken, "Payment token");
    }

    public Money totalPrice() {
        return burgerType.priceFor(quantity);
    }

    private static void requireNonBlank(String value, String name) {
        Objects.requireNonNull(value, name + " must not be null");
        if (value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
    }
}
