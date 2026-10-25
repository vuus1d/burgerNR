package strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentDiscountTest {
    private final DiscountStrategy discount = new StudentDiscount();

    @Test
    void applyDiscount_priceOf1000_returns900() {
        assertEquals(900, discount.applyDiscount(1000));
    }

    @Test
    void applyDiscount_priceOf2490_returns2241() {
        assertEquals(2241, discount.applyDiscount(2490));
    }
}
