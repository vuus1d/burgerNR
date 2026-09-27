package bridge;

import java.util.List;
import java.util.Objects;

/**
 * Abstraction: describes WHAT burger is made. HOW it is cooked is delegated to a CookingMethod (the bridge).
 */
public abstract class BurgerRecipe {
    private CookingMethod cookingMethod;

    protected BurgerRecipe(CookingMethod cookingMethod) {
        this.cookingMethod = requireCookingMethod(cookingMethod);
    }

    public void changeCookingMethod(CookingMethod cookingMethod) {
        this.cookingMethod = requireCookingMethod(cookingMethod);
    }

    public final void prepare() {
        System.out.println("Preparing " + getName() + " using " + cookingMethod.getName());
        System.out.println("  - " + cookingMethod.toastBun(getBun()));
        System.out.println("  - " + cookingMethod.cookPatty(getPatty()));
        System.out.println("  - Adding toppings: " + String.join(", ", getToppings()));
    }

    protected abstract String getName();

    protected abstract String getBun();

    protected abstract String getPatty();

    protected abstract List<String> getToppings();

    private static CookingMethod requireCookingMethod(CookingMethod cookingMethod) {
        return Objects.requireNonNull(cookingMethod, "Cooking method must not be null");
    }
}
