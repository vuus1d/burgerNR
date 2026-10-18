package facade.subsystem;

import facade.model.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimulatedPaymentGatewayTest {
    private static final Money LIMIT = Money.of("10000");

    private SimulatedPaymentGateway gateway;

    @BeforeEach
    void setUp() {
        gateway = new SimulatedPaymentGateway(LIMIT);
    }

    @Test
    void charge_validToken_returnsNumberedReceiptForTheAmount() {
        Receipt receipt = gateway.charge("tok_ok", Money.of("2490"));

        assertEquals(new Receipt("RCPT-1", Money.of("2490")), receipt);
    }

    @Test
    void charge_validToken_capturesTheAmount() {
        gateway.charge("tok_ok", Money.of("2490"));

        assertEquals(Money.of("2490"), gateway.totalCaptured());
    }

    @Test
    void charge_blankToken_isDeclined() {
        assertThrows(PaymentDeclinedException.class, () -> gateway.charge(" ", Money.of("2490")));
    }

    @Test
    void charge_declinedToken_isDeclined() {
        assertThrows(PaymentDeclinedException.class,
                () -> gateway.charge(SimulatedPaymentGateway.DECLINED_TOKEN, Money.of("2490")));
    }

    @Test
    void charge_amountAboveLimit_isDeclinedWithAmountAndLimit() {
        PaymentDeclinedException failure = assertThrows(PaymentDeclinedException.class,
                () -> gateway.charge("tok_ok", Money.of("10000.01")));

        assertEquals("Charge of 10000.01 KZT declined: exceeds the limit of 10000.00 KZT", failure.getMessage());
    }

    @Test
    void charge_amountEqualToLimit_isAccepted() {
        gateway.charge("tok_ok", LIMIT);

        assertEquals(LIMIT, gateway.totalCaptured());
    }

    @Test
    void refund_givesTheCapturedAmountBack() {
        Receipt receipt = gateway.charge("tok_ok", Money.of("2490"));

        gateway.refund(receipt);

        assertEquals(Money.ZERO, gateway.totalCaptured());
    }

    @Test
    void refund_sameReceiptTwice_isRejected() {
        Receipt receipt = gateway.charge("tok_ok", Money.of("2490"));
        gateway.refund(receipt);

        assertThrows(IllegalStateException.class, () -> gateway.refund(receipt));
    }
}
