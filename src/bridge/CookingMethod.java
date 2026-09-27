package bridge;

/**
 * Implementor: low-level kitchen operations that every cooking method must provide.
 */
public interface CookingMethod {
    String getName();

    String toastBun(String bun);

    String cookPatty(String patty);
}
