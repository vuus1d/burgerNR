package bridge;

import java.util.List;

/**
 * Refined Abstraction
 */
public class ChickenBurgerRecipe extends BurgerRecipe {
    public ChickenBurgerRecipe(CookingMethod cookingMethod) {
        super(cookingMethod);
    }

    @Override
    protected String getName() {
        return "Chicken Burger";
    }

    @Override
    protected String getBun() {
        return "Sesame Bun";
    }

    @Override
    protected String getPatty() {
        return "Chicken Fillet";
    }

    @Override
    protected List<String> getToppings() {
        return List.of("Swiss", "Lettuce", "Garlic Mayo");
    }
}
