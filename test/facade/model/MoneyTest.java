package facade.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MoneyTest {
    @Test
    void of_sameAmountWithDifferentScale_isEqual() {
        assertEquals(Money.of("2490"), Money.of("2490.00"));
    }

    @Test
    void of_moreThanTwoDecimals_roundsHalfUp() {
        assertEquals(Money.of("0.13"), Money.of("0.125"));
    }

    @Test
    void of_negativeAmount_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> Money.of("-1"));
    }

    @Test
    void times_multipliesTheAmount() {
        assertEquals(Money.of("7470"), Money.of("2490").times(3));
    }

    @Test
    void toString_showsTwoDecimalsAndCurrency() {
        assertEquals("2490.00 KZT", Money.of("2490").toString());
    }
}
