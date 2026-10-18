package facade;

import facade.model.FailureReason;

import java.util.Objects;

/**
 * Facade exception: the only failure a client of BurgerOrderFacade has to handle.
 */
public class OrderFailedException extends RuntimeException {
    private final FailureReason reason;

    public OrderFailedException(FailureReason reason, String message, Throwable cause) {
        super(message, cause);
        this.reason = Objects.requireNonNull(reason, "Failure reason must not be null");
    }

    public FailureReason reason() {
        return reason;
    }
}
