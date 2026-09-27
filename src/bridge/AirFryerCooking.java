package bridge;

/**
 * Concrete Implementor
 */
public class AirFryerCooking implements CookingMethod {
    @Override
    public String getName() {
        return "Air Fryer";
    }

    @Override
    public String toastBun(String bun) {
        return "Warming " + bun + " in the air fryer basket";
    }

    @Override
    public String cookPatty(String patty) {
        return "Air-frying " + patty + " with hot circulating air";
    }
}
