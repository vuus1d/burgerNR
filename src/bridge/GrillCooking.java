package bridge;

/**
 * Concrete Implementor
 */
public class GrillCooking implements CookingMethod {
    @Override
    public String getName() {
        return "Grill";
    }

    @Override
    public String toastBun(String bun) {
        return "Toasting " + bun + " on the grill grates";
    }

    @Override
    public String cookPatty(String patty) {
        return "Grilling " + patty + " over an open flame";
    }
}
