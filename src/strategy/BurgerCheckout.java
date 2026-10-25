package strategy;

import java.util.Objects;

/**
 * Context: calculates the order total. HOW the discount is calculated is delegated to a DiscountStrategy.
 */
public class BurgerCheckout {
    private DiscountStrategy discountStrategy;

    public BurgerCheckout(DiscountStrategy discountStrategy) {
        this.discountStrategy = requireDiscountStrategy(discountStrategy);
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        this.discountStrategy = requireDiscountStrategy(discountStrategy);
    }

    public int calculateTotal(int priceInTenge) {
        return discountStrategy.applyDiscount(priceInTenge);
    }

    private static DiscountStrategy requireDiscountStrategy(DiscountStrategy discountStrategy) {
        return Objects.requireNonNull(discountStrategy, "Discount strategy must not be null");
    }
}
