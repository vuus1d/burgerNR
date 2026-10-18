package facade.model;

import java.time.Instant;

/**
 * Domain value: the result of a successfully placed order.
 */
public record OrderConfirmation(String orderId, BurgerType burgerType, int quantity, Money totalPaid,
                                Instant placedAt, Instant estimatedDelivery) {
}
