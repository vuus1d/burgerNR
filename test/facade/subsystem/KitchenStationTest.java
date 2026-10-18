package facade.subsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static facade.model.BurgerType.CHICKEN;
import static facade.model.BurgerType.SMASH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KitchenStationTest {
    private static final int CAPACITY = 3;

    private KitchenStation kitchen;

    @BeforeEach
    void setUp() {
        kitchen = new KitchenStation(CAPACITY);
    }

    @Test
    void cook_returnsOneBurgerPerOrderedUnit() {
        List<PreparedBurger> burgers = kitchen.cook(CHICKEN, 2);

        assertEquals(List.of(CHICKEN, CHICKEN), burgers.stream().map(PreparedBurger::burgerType).toList());
    }

    @Test
    void cook_performsStepsInFixedOrder() {
        PreparedBurger burger = kitchen.cook(SMASH, 1).get(0);

        assertEquals(List.of(CookingStep.TOAST_BUN, CookingStep.COOK_PATTY, CookingStep.ASSEMBLE, CookingStep.QUALITY_CHECK),
                burger.completedSteps());
    }

    @Test
    void cook_quantityAboveCapacity_throwsKitchenExceptionWithContext() {
        KitchenException failure = assertThrows(KitchenException.class, () -> kitchen.cook(SMASH, CAPACITY + 1));

        assertEquals("Cannot cook 4 x SMASH: station capacity is 3 burgers per order", failure.getMessage());
    }

    @Test
    void cook_zeroQuantity_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> kitchen.cook(SMASH, 0));
    }
}
