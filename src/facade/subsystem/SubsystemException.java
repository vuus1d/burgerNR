package facade.subsystem;

/**
 * Subsystem class: unchecked base of every failure the subsystem reports, for clients that use it directly.
 */
public abstract sealed class SubsystemException extends RuntimeException
        permits OutOfStockException, PaymentDeclinedException, KitchenException, DeliveryException {
    protected SubsystemException(String message) {
        super(message);
    }
}
