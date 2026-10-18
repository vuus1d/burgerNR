package facade.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Domain value: an amount in tenge. Hides BigDecimal and always keeps two decimals, so equal amounts are equal.
 */
public final class Money {
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;
    private static final String CURRENCY = "KZT";

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    private final BigDecimal amount;

    private Money(BigDecimal amount) {
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("Money amount must not be negative, but was " + amount);
        }
        this.amount = amount.setScale(SCALE, ROUNDING);
    }

    public static Money of(String amount) {
        Objects.requireNonNull(amount, "Money amount must not be null");
        return new Money(new BigDecimal(amount));
    }

    public Money plus(Money other) {
        return new Money(amount.add(other.amount));
    }

    public Money minus(Money other) {
        return new Money(amount.subtract(other.amount));
    }

    public Money times(int factor) {
        return new Money(amount.multiply(BigDecimal.valueOf(factor)));
    }

    public boolean isGreaterThan(Money other) {
        return amount.compareTo(other.amount) > 0;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Money money && amount.equals(money.amount);
    }

    @Override
    public int hashCode() {
        return amount.hashCode();
    }

    @Override
    public String toString() {
        return amount.toPlainString() + " " + CURRENCY;
    }
}
