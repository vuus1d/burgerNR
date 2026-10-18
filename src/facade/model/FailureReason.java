package facade.model;

/**
 * Domain value: why an order could not be placed, in terms the customer understands.
 */
public enum FailureReason {
    OUT_OF_STOCK,
    PAYMENT_DECLINED,
    KITCHEN_UNAVAILABLE,
    DELIVERY_UNAVAILABLE
}
