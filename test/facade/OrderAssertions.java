package facade;

import facade.model.FailureReason;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test language: domain-level assertions about failed orders.
 */
final class OrderAssertions {
    private OrderAssertions() {
    }

    static OrderFailedException assertOrderFailedBecause(FailureReason expected, Executable placingTheOrder) {
        OrderFailedException failure = assertThrows(OrderFailedException.class, placingTheOrder);
        assertEquals(expected, failure.reason(), "failure reason");
        return failure;
    }

    static void assertMentions(String message, String... expectedFragments) {
        for (String fragment : expectedFragments) {
            assertTrue(message.contains(fragment), () -> "Expected '" + message + "' to mention '" + fragment + "'");
        }
    }
}
