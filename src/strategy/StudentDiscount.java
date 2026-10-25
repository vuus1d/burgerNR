package strategy;

/**
 * Concrete Strategy: 10% off for students.
 */
public class StudentDiscount implements DiscountStrategy {
    private static final int DISCOUNT_PERCENT = 10;

    @Override
    public int applyDiscount(int priceInTenge) {
        return priceInTenge * (100 - DISCOUNT_PERCENT) / 100;
    }

    @Override
    public String getName() {
        return "Student discount (-" + DISCOUNT_PERCENT + "%)";
    }
}
