package strategy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BurgerCheckoutTest {
    @Test
    void calculateTotal_withStudentDiscount_returnsDiscountedPrice() {
        BurgerCheckout checkout = new BurgerCheckout(new StudentDiscount());

        assertEquals(900, checkout.calculateTotal(1000));
    }

    @Test
    void calculateTotal_afterSetDiscountStrategy_usesNewStrategy() {
        BurgerCheckout checkout = new BurgerCheckout(new NoDiscount());

        checkout.setDiscountStrategy(new HappyHourDiscount());

        assertEquals(800, checkout.calculateTotal(1000));
    }

    @Test
    void constructor_nullStrategy_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BurgerCheckout(null));
    }

    @Test
    void setDiscountStrategy_nullStrategy_throwsNullPointerException() {
        BurgerCheckout checkout = new BurgerCheckout(new NoDiscount());

        assertThrows(NullPointerException.class, () -> checkout.setDiscountStrategy(null));
    }
}
