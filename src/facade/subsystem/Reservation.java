package facade.subsystem;

import facade.model.BurgerType;

/**
 * Subsystem class: ingredients set aside for an order. Compared by identity, so each one is released exactly once.
 */
public final class Reservation {
    private final BurgerType burgerType;
    private final int quantity;

    Reservation(BurgerType burgerType, int quantity) {
        this.burgerType = burgerType;
        this.quantity = quantity;
    }

    public BurgerType burgerType() {
        return burgerType;
    }

    public int quantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return quantity + " x " + burgerType;
    }
}
