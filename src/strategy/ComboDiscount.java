package strategy;

/**
 * Concrete Strategy: a fixed 500 tenge off for a combo. The price never goes below 0.
 */
public class ComboDiscount implements DiscountStrategy {
    private static final int DISCOUNT_IN_TENGE = 500;

    @Override
    public int applyDiscount(int priceInTenge) {
        return Math.max(0, priceInTenge - DISCOUNT_IN_TENGE);
    }

    @Override
    public String getName() {
        return "Combo discount (-" + DISCOUNT_IN_TENGE + " tenge)";
    }
}
