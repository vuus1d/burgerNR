package facade;

import facade.model.Money;
import facade.subsystem.PaymentDeclinedException;
import facade.subsystem.PaymentGateway;
import facade.subsystem.Receipt;

import java.util.ArrayList;
import java.util.List;

/**
 * Hand-written fake: records charges and refunds, and can be told to decline charges or to fail refunds.
 */
final class FakePaymentGateway implements PaymentGateway {
    private final List<String> journal;
    private final List<Money> chargedAmounts = new ArrayList<>();
    private boolean decliningCharges;
    private boolean failingRefunds;

    FakePaymentGateway(List<String> journal) {
        this.journal = journal;
    }

    void declineEveryCharge() {
        decliningCharges = true;
    }

    void failEveryRefund() {
        failingRefunds = true;
    }

    List<Money> chargedAmounts() {
        return chargedAmounts;
    }

    @Override
    public Receipt charge(String paymentToken, Money amount) {
        if (decliningCharges) {
            throw new PaymentDeclinedException("Fake gateway declines " + amount);
        }
        chargedAmounts.add(amount);
        journal.add("charge");
        return new Receipt("FAKE-" + chargedAmounts.size(), amount);
    }

    @Override
    public void refund(Receipt receipt) {
        if (failingRefunds) {
            throw new IllegalStateException("Fake gateway cannot refund " + receipt.receiptId());
        }
        chargedAmounts.remove(receipt.amount());
        journal.add("refund");
    }
}
