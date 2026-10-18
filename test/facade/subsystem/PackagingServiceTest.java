package facade.subsystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static facade.model.BurgerType.SMASH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PackagingServiceTest {
    private PackagingService packaging;

    @BeforeEach
    void setUp() {
        packaging = new PackagingService();
    }

    @Test
    void pack_upToBagCapacity_usesPaperBag() {
        PackedOrder packedOrder = packaging.pack(checkedBurgers(PackagingService.PAPER_BAG_CAPACITY));

        assertEquals(new PackedOrder(PackedOrder.Packaging.PAPER_BAG, 2), packedOrder);
    }

    @Test
    void pack_moreThanBagCapacity_usesBox() {
        PackedOrder packedOrder = packaging.pack(checkedBurgers(PackagingService.PAPER_BAG_CAPACITY + 1));

        assertEquals(new PackedOrder(PackedOrder.Packaging.BOX, 3), packedOrder);
    }

    @Test
    void pack_noBurgers_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> packaging.pack(List.of()));
    }

    @Test
    void pack_burgerWithoutQualityCheck_isRejected() {
        PreparedBurger unchecked = new PreparedBurger(SMASH, List.of(CookingStep.TOAST_BUN, CookingStep.COOK_PATTY));

        assertThrows(IllegalArgumentException.class, () -> packaging.pack(List.of(unchecked)));
    }

    private static List<PreparedBurger> checkedBurgers(int count) {
        return Collections.nCopies(count, new PreparedBurger(SMASH, List.of(CookingStep.values())));
    }
}
