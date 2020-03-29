package dev.luke10x.starling.roundup.backend;

public class Money {
    private final String currency;
    private final int minorUnits;
    public Money(String currency, int minorUnits) {
        this.currency = currency;
        this.minorUnits = minorUnits;
    }

    public String getCurrecy() {
        return currency;
    }

    public int getMinorUnits() {
        return minorUnits;
    }
}
