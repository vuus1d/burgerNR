package facade.subsystem;

import facade.model.Money;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Subsystem class: deterministic in-memory stand-in for the payment provider that does not exist yet.
 */
public class SimulatedPaymentGateway implements PaymentGateway {
    public static final String DECLINED_TOKEN = "tok_declined";

    private final Money chargeLimit;
    private final Set<Receipt> refundableReceipts = new HashSet<>();
    private Money totalCaptured = Money.ZERO;
    private int nextReceiptNumber = 1;

    public SimulatedPaymentGateway(Money chargeLimit) {
        this.chargeLimit = Objects.requireNonNull(chargeLimit, "Charge limit must not be null");
    }

    @Override
    public Receipt charge(String paymentToken, Money amount) {
        Objects.requireNonNull(paymentToken, "Payment token must not be null");
        Objects.requireNonNull(amount, "Amount must not be null");
        rejectIfDeclined(paymentToken, amount);
        Receipt receipt = new Receipt("RCPT-" + nextReceiptNumber++, amount);
        refundableReceipts.add(receipt);
        totalCaptured = totalCaptured.plus(amount);
        return receipt;
    }

    @Override
    public void refund(Receipt receipt) {
        Objects.requireNonNull(receipt, "Receipt must not be null");
        if (!refundableReceipts.remove(receipt)) {
            throw new IllegalStateException("Cannot refund " + receipt.receiptId() + ": it is unknown or already refunded");
        }
        totalCaptured = totalCaptured.minus(receipt.amount());
    }

    public Money totalCaptured() {
        return totalCaptured;
    }

    private void rejectIfDeclined(String paymentToken, Money amount) {
        if (paymentToken.isBlank()) {
            throw new PaymentDeclinedException("Charge of " + amount + " declined: payment token is blank");
        }
        if (paymentToken.equals(DECLINED_TOKEN)) {
            throw new PaymentDeclinedException("Charge of " + amount + " declined by the card issuer");
        }
        if (amount.isGreaterThan(chargeLimit)) {
            throw new PaymentDeclinedException("Charge of " + amount + " declined: exceeds the limit of " + chargeLimit);
        }
    }
}
