package strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ComboDiscountTest {
    private final DiscountStrategy discount = new ComboDiscount();

    @Test
    void applyDiscount_priceOf1000_returns500() {
        assertEquals(500, discount.applyDiscount(1000));
    }

    @Test
    void applyDiscount_priceOf500_returns0() {
        assertEquals(0, discount.applyDiscount(500));
    }

    @Test
    void applyDiscount_priceBelow500_returns0() {
        assertEquals(0, discount.applyDiscount(300));
    }
}
