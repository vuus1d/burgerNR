package facade.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderRequestTest {
    private static final String ADDRESS = "Zone A, Mangilik El 55";
    private static final String TOKEN = "tok_ok";

    @Test
    void totalPrice_isUnitPriceTimesQuantity() {
        OrderRequest request = new OrderRequest(BurgerType.CHICKEN, 3, ADDRESS, TOKEN);

        assertEquals(Money.of("6570"), request.totalPrice());
    }

    @Test
    void create_nullBurgerType_throwsNullPointerExceptionWithMessage() {
        NullPointerException failure = assertThrows(NullPointerException.class,
                () -> new OrderRequest(null, 1, ADDRESS, TOKEN));

        assertEquals("Burger type must not be null", failure.getMessage());
    }

    @Test
    void create_zeroQuantity_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> new OrderRequest(BurgerType.SMASH, 0, ADDRESS, TOKEN));
    }

    @Test
    void create_quantityAboveMaximum_isRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> new OrderRequest(BurgerType.SMASH, OrderRequest.MAX_QUANTITY + 1, ADDRESS, TOKEN));
    }

    @Test
    void create_blankAddress_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> new OrderRequest(BurgerType.SMASH, 1, " ", TOKEN));
    }

    @Test
    void create_blankPaymentToken_isRejected() {
        assertThrows(IllegalArgumentException.class, () -> new OrderRequest(BurgerType.SMASH, 1, ADDRESS, ""));
    }
}
