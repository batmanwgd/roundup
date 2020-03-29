package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.accounts.Account;

import java.time.LocalDate;

public class RoundupCalculatedEvent {
    private final LocalDate from;

    public RoundupCalculatedEvent(LocalDate from) {
        this.from = from;
    }

    public LocalDate getFrom() {
        return from;
    }

    public Account getAccount() {
        return null;
    }
}
