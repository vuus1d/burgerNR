package facade.subsystem;

import facade.model.BurgerType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Subsystem class: cooks an order in a fixed sequence of steps, up to the station's capacity.
 */
public class KitchenStation {
    private static final List<CookingStep> STEPS_IN_ORDER = List.of(CookingStep.values());

    private final int capacity;

    public KitchenStation(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Kitchen capacity must be positive, but was " + capacity);
        }
        this.capacity = capacity;
    }

    public List<PreparedBurger> cook(BurgerType burgerType, int quantity) {
        Objects.requireNonNull(burgerType, "Burger type must not be null");
        if (quantity < 1) {
            throw new IllegalArgumentException("Quantity to cook must be positive, but was " + quantity);
        }
        if (quantity > capacity) {
            throw new KitchenException(String.format(
                    "Cannot cook %d x %s: station capacity is %d burgers per order", quantity, burgerType, capacity));
        }
        List<PreparedBurger> burgers = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            burgers.add(new PreparedBurger(burgerType, STEPS_IN_ORDER));
        }
        return List.copyOf(burgers);
    }
}
