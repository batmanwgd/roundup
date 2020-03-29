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
}
