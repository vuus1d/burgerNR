package facade.subsystem;

import facade.model.Money;

/**
 * Subsystem class: proof of a successful charge, needed to refund it.
 */
public record Receipt(String receiptId, Money amount) {
}
