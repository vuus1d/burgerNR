package strategy;

import java.util.List;

/**
 * Client: creates the strategies and switches them in the same checkout at runtime.
 */
public class StrategyDemo {
    private static final int BURGER_PRICE_IN_TENGE = 2490;

    public static void main(String[] args) {
        System.out.println("=== STRATEGY PATTERN DEMO ===");
        System.out.println();
        System.out.println("Burger price: " + BURGER_PRICE_IN_TENGE + " tenge");
        System.out.println();

        List<DiscountStrategy> strategies = List.of(
                new NoDiscount(),
                new StudentDiscount(),
                new HappyHourDiscount(),
                new ComboDiscount()
        );

        BurgerCheckout checkout = new BurgerCheckout(new NoDiscount());

        for (DiscountStrategy strategy : strategies) {
            checkout.setDiscountStrategy(strategy);
            System.out.println(strategy.getName() + ": "
                    + checkout.calculateTotal(BURGER_PRICE_IN_TENGE) + " tenge");
        }
    }
}
