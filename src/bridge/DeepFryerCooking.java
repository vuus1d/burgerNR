package bridge;

/**
 * Concrete Implementor
 */
public class DeepFryerCooking implements CookingMethod {
    @Override
    public String getName() {
        return "Deep Fryer";
    }

    @Override
    public String toastBun(String bun) {
        return "Toasting " + bun + " on the flat top next to the fryer";
    }

    @Override
    public String cookPatty(String patty) {
        return "Deep-frying " + patty + " until golden and crispy";
    }
}
