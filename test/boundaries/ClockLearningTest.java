package boundaries;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Learning test: the java.time behaviour that DeliveryDispatcher and the tests rely on.
 */
class ClockLearningTest {
    private static final Instant NOON = Instant.parse("2026-10-18T12:00:00Z");

    @Test
    void fixedClock_alwaysReturnsTheSameInstant() {
        Clock clock = Clock.fixed(NOON, ZoneOffset.UTC);

        assertEquals(clock.instant(), clock.instant());
    }

    @Test
    void instantPlusDuration_isExactArithmetic() {
        assertEquals(Instant.parse("2026-10-18T12:20:00Z"), NOON.plus(Duration.ofMinutes(20)));
    }

    @Test
    void offsetClock_isShiftedByTheDuration() {
        Clock later = Clock.offset(Clock.fixed(NOON, ZoneOffset.UTC), Duration.ofMinutes(20));

        assertEquals(NOON.plus(Duration.ofMinutes(20)), later.instant());
    }

    @Test
    void isBefore_isStrict() {
        assertFalse(NOON.isBefore(NOON));
    }
}
