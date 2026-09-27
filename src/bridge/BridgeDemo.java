package bridge;

import java.util.List;

/**
 * Client: links a recipe with a cooking method at runtime
 */
public class BridgeDemo {
    public static void main(String[] args) {
        System.out.println("=== BRIDGE PATTERN DEMO ===");
        System.out.println();

        demonstrateAllCombinations();
        demonstrateRuntimeSwitching();
    }

    private static void demonstrateAllCombinations() {
        System.out.println("1) Any recipe works with any cooking method:");
        System.out.println();

        for (CookingMethod method : List.of(new GrillCooking(), new DeepFryerCooking())) {
            new SmashBurgerRecipe(method).prepare();
            new ChickenBurgerRecipe(method).prepare();
            System.out.println();
        }
    }

    private static void demonstrateRuntimeSwitching() {
        System.out.println("2) Switching the cooking method of the same burger at runtime:");
        System.out.println();

        BurgerRecipe chickenBurger = new ChickenBurgerRecipe(new GrillCooking());
        chickenBurger.prepare();
        System.out.println();

        chickenBurger.changeCookingMethod(new DeepFryerCooking());
        chickenBurger.prepare();
        System.out.println();

        chickenBurger.changeCookingMethod(new AirFryerCooking());
        chickenBurger.prepare();
    }
}
