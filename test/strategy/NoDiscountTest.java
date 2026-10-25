package strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NoDiscountTest {
    private final DiscountStrategy discount = new NoDiscount();

    @Test
    void applyDiscount_priceOf1000_returns1000() {
        assertEquals(1000, discount.applyDiscount(1000));
    }

    @Test
    void applyDiscount_priceOf0_returns0() {
        assertEquals(0, discount.applyDiscount(0));
    }
}
