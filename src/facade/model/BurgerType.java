package facade.model;

import java.util.List;

/**
 * Domain value: a burger on the menu with its unit price and recipe.
 */
public enum BurgerType {
    SMASH("Smash Burger", "2490",
            Ingredient.BUN, Ingredient.BEEF_PATTY, Ingredient.BEEF_PATTY, Ingredient.CHEESE, Ingredient.SAUCE),
    CHICKEN("Chicken Burger", "2190",
            Ingredient.BUN, Ingredient.CHICKEN_FILLET, Ingredient.LETTUCE, Ingredient.SAUCE);

    private final String displayName;
    private final Money unitPrice;
    private final List<Ingredient> recipe;

    BurgerType(String displayName, String unitPrice, Ingredient... recipe) {
        this.displayName = displayName;
        this.unitPrice = Money.of(unitPrice);
        this.recipe = List.of(recipe);
    }

    public String displayName() {
        return displayName;
    }

    public Money priceFor(int quantity) {
        return unitPrice.times(quantity);
    }

    public int unitsOf(Ingredient ingredient) {
        return (int) recipe.stream().filter(ingredient::equals).count();
    }
}
