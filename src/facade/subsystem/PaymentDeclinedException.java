package facade.subsystem;

/**
 * Subsystem class: thrown when the payment provider refuses a charge.
 */
public final class PaymentDeclinedException extends SubsystemException {
    public PaymentDeclinedException(String message) {
        super(message);
    }
}
