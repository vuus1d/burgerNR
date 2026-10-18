package facade.subsystem;

/**
 * Subsystem class: thrown when a delivery cannot be scheduled.
 */
public final class DeliveryException extends SubsystemException {
    public DeliveryException(String message) {
        super(message);
    }
}
