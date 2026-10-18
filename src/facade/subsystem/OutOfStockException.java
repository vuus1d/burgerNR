package facade.subsystem;

import facade.model.BurgerType;
import facade.model.Ingredient;

/**
 * Subsystem class: thrown when the stock cannot cover a reservation.
 */
public final class OutOfStockException extends SubsystemException {
    private final Ingredient ingredient;
    private final int requested;
    private final int available;

    public OutOfStockException(BurgerType burgerType, int quantity, Ingredient ingredient, int requested, int available) {
        super(String.format("Cannot reserve %d x %s: needs %d %s, only %d in stock",
                quantity, burgerType, requested, ingredient, available));
        this.ingredient = ingredient;
        this.requested = requested;
        this.available = available;
    }

    public Ingredient ingredient() {
        return ingredient;
    }

    public int requested() {
        return requested;
    }

    public int available() {
        return available;
    }
}
