package strategy;

/**
 * Concrete Strategy: the price does not change.
 */
public class NoDiscount implements DiscountStrategy {
    @Override
    public int applyDiscount(int priceInTenge) {
        return priceInTenge;
    }

    @Override
    public String getName() {
        return "No discount";
    }
}
