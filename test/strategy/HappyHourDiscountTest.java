package strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HappyHourDiscountTest {
    private final DiscountStrategy discount = new HappyHourDiscount();

    @Test
    void applyDiscount_priceOf1000_returns800() {
        assertEquals(800, discount.applyDiscount(1000));
    }

    @Test
    void applyDiscount_priceOf2490_returns1992() {
        assertEquals(1992, discount.applyDiscount(2490));
    }
}
