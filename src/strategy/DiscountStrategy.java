package strategy;

/**
 * Strategy: a way to calculate the discounted price of an order.
 */
public interface DiscountStrategy {
    int applyDiscount(int priceInTenge);

    String getName();
}
