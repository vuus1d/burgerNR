package bridge;

import java.util.List;

/**
 * Refined Abstraction
 */
public class SmashBurgerRecipe extends BurgerRecipe {
    public SmashBurgerRecipe(CookingMethod cookingMethod) {
        super(cookingMethod);
    }

    @Override
    protected String getName() {
        return "Smash Burger";
    }

    @Override
    protected String getBun() {
        return "Brioche Bun";
    }

    @Override
    protected String getPatty() {
        return "Double Beef Patty";
    }

    @Override
    protected List<String> getToppings() {
        return List.of("Cheddar", "Pickles", "Smoky BBQ");
    }
}
