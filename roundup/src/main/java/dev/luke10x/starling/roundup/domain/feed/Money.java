package dev.luke10x.starling.roundup.domain.feed;

public class Money {
    private String currency;
    private int minorUnits;

    public Money() {}

    public Money(String currency, int minorUnits) {
        this.currency = currency;
        this.minorUnits = minorUnits;
    }

    public String getCurrency() {
        return currency;
    }

    public int getMinorUnits() {
        return minorUnits;
    }

    public Money add(Money another) {
        return new Money(this.currency, this.minorUnits + another.minorUnits);
    }

    public Money roundup() {
        return new Money(this.currency, 100 - this.minorUnits % 100);
    }

    public String toString() {
        // TODO: JPY case
        return this.currency + String.format(" %.2f", (double) this.minorUnits / 100);
    }
}
