package facade.subsystem;

import facade.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static facade.model.BurgerType.CHICKEN;
import static facade.model.BurgerType.SMASH;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InventoryServiceTest {
    private InventoryService inventory;

    @BeforeEach
    void setUp() {
        inventory = new InventoryService();
        inventory.restock(Ingredient.BUN, 10);
        inventory.restock(Ingredient.BEEF_PATTY, 10);
        inventory.restock(Ingredient.CHEESE, 10);
        inventory.restock(Ingredient.SAUCE, 10);
    }

    @Test
    void reserve_smashBurgers_takesTwoPattiesPerBurger() {
        inventory.reserve(SMASH, 3);

        assertEquals(4, inventory.stockOf(Ingredient.BEEF_PATTY));
    }

    @Test
    void reserve_notEnoughStock_reportsIngredientRequestedAndAvailable() {
        OutOfStockException failure = assertThrows(OutOfStockException.class, () -> inventory.reserve(SMASH, 6));

        assertEquals("Cannot reserve 6 x SMASH: needs 12 BEEF_PATTY, only 10 in stock", failure.getMessage());
    }

    @Test
    void reserve_notEnoughStock_leavesEveryIngredientUntouched() {
        assertThrows(OutOfStockException.class, () -> inventory.reserve(SMASH, 6));

        assertEquals(10, inventory.stockOf(Ingredient.BUN));
    }

    @Test
    void reserve_ingredientNeverStocked_isOutOfStock() {
        assertThrows(OutOfStockException.class, () -> inventory.reserve(CHICKEN, 1));
    }

    @Test
    void release_returnsReservedIngredientsToStock() {
        Reservation reservation = inventory.reserve(SMASH, 3);

        inventory.release(reservation);

        assertEquals(10, inventory.stockOf(Ingredient.BEEF_PATTY));
    }

    @Test
    void release_sameReservationTwice_isRejected() {
        Reservation reservation = inventory.reserve(SMASH, 1);
        inventory.release(reservation);

        assertThrows(IllegalStateException.class, () -> inventory.release(reservation));
    }

    @Test
    void restock_addsToExistingStock() {
        inventory.restock(Ingredient.BUN, 5);

        assertEquals(15, inventory.stockOf(Ingredient.BUN));
    }

    @Test
    void stockOf_ingredientNeverStocked_isZero() {
        assertEquals(0, inventory.stockOf(Ingredient.CHICKEN_FILLET));
    }
}
