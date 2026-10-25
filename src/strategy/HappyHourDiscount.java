package strategy;

/**
 * Concrete Strategy: 20% off during happy hour.
 */
public class HappyHourDiscount implements DiscountStrategy {
    private static final int DISCOUNT_PERCENT = 20;

    @Override
    public int applyDiscount(int priceInTenge) {
        return priceInTenge * (100 - DISCOUNT_PERCENT) / 100;
    }

    @Override
    public String getName() {
        return "Happy hour discount (-" + DISCOUNT_PERCENT + "%)";
    }
}
