package facade.subsystem;

import facade.model.BurgerType;
import facade.model.Ingredient;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Subsystem class: keeps the ingredient stock and reserves what an order needs, all or nothing.
 */
public class InventoryService {
    private final Map<Ingredient, Integer> stock = new EnumMap<>(Ingredient.class);
    private final Set<Reservation> activeReservations = new HashSet<>();

    public void restock(Ingredient ingredient, int units) {
        Objects.requireNonNull(ingredient, "Ingredient must not be null");
        requirePositive(units, "Restock units");
        stock.merge(ingredient, units, Integer::sum);
    }

    public int stockOf(Ingredient ingredient) {
        Objects.requireNonNull(ingredient, "Ingredient must not be null");
        return stock.getOrDefault(ingredient, 0);
    }

    public Reservation reserve(BurgerType burgerType, int quantity) {
        Objects.requireNonNull(burgerType, "Burger type must not be null");
        requirePositive(quantity, "Reserved quantity");
        for (Ingredient ingredient : Ingredient.values()) {
            ensureAvailable(burgerType, quantity, ingredient);
        }
        adjustStock(burgerType, -quantity);
        Reservation reservation = new Reservation(burgerType, quantity);
        activeReservations.add(reservation);
        return reservation;
    }

    public void release(Reservation reservation) {
        Objects.requireNonNull(reservation, "Reservation must not be null");
        if (!activeReservations.remove(reservation)) {
            throw new IllegalStateException("Cannot release reservation of " + reservation
                    + ": it is unknown or already released");
        }
        adjustStock(reservation.burgerType(), reservation.quantity());
    }

    private void ensureAvailable(BurgerType burgerType, int quantity, Ingredient ingredient) {
        int requested = burgerType.unitsOf(ingredient) * quantity;
        int available = stockOf(ingredient);
        if (requested > available) {
            throw new OutOfStockException(burgerType, quantity, ingredient, requested, available);
        }
    }

    private void adjustStock(BurgerType burgerType, int burgers) {
        for (Ingredient ingredient : Ingredient.values()) {
            int units = burgerType.unitsOf(ingredient) * burgers;
            if (units != 0) {
                stock.merge(ingredient, units, Integer::sum);
            }
        }
    }

    private static void requirePositive(int value, String name) {
        if (value < 1) {
            throw new IllegalArgumentException(name + " must be positive, but was " + value);
        }
    }
}
