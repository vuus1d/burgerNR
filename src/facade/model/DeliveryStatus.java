package facade.model;

/**
 * Domain value: where an order is. UNKNOWN is the special case for an order id nobody has scheduled.
 */
public enum DeliveryStatus {
    ON_THE_WAY,
    DELIVERED,
    UNKNOWN
}
