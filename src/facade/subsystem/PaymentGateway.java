package facade.subsystem;

import facade.model.Money;

/**
 * Subsystem class: the payment operations our system needs. A real provider is plugged in behind it with an adapter.
 */
public interface PaymentGateway {
    Receipt charge(String paymentToken, Money amount);

    void refund(Receipt receipt);
}
