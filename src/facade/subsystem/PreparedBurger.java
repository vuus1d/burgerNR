package facade.subsystem;

import facade.model.BurgerType;

import java.util.List;
import java.util.Objects;

/**
 * Subsystem class: a burger that left the kitchen, with the steps it went through.
 */
public record PreparedBurger(BurgerType burgerType, List<CookingStep> completedSteps) {
    public PreparedBurger {
        Objects.requireNonNull(burgerType, "Burger type must not be null");
        completedSteps = List.copyOf(completedSteps);
    }

    public boolean passedQualityCheck() {
        return completedSteps.contains(CookingStep.QUALITY_CHECK);
    }
}
