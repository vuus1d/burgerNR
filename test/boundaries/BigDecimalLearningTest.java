package boundaries;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Learning test: the BigDecimal behaviour that Money relies on.
 */
class BigDecimalLearningTest {
    @Test
    void equals_takesScaleIntoAccount() {
        assertNotEquals(new BigDecimal("2.0"), new BigDecimal("2.00"));
    }

    @Test
    void compareTo_ignoresScale() {
        assertEquals(0, new BigDecimal("2.0").compareTo(new BigDecimal("2.00")));
    }

    @Test
    void setScale_makesEqualAmountsEqual() {
        assertEquals(new BigDecimal("2.00"), new BigDecimal("2").setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void setScale_halfUp_roundsTiesAwayFromZero() {
        assertEquals(new BigDecimal("2.35"), new BigDecimal("2.345").setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void setScale_halfEven_roundsTiesToEvenNeighbour() {
        assertEquals(new BigDecimal("2.34"), new BigDecimal("2.345").setScale(2, RoundingMode.HALF_EVEN));
    }

    @Test
    void setScale_withoutRoundingMode_throwsWhenDigitsWouldBeLost() {
        assertThrows(ArithmeticException.class, () -> new BigDecimal("2.345").setScale(2));
    }
}
