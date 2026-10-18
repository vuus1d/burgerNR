package facade.subsystem;

/**
 * Subsystem class: thrown when the kitchen cannot cook an order.
 */
public final class KitchenException extends SubsystemException {
    public KitchenException(String message) {
        super(message);
    }
}
