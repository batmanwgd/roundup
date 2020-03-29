package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.accounts.Account;
import dev.luke10x.starling.roundup.domain.feed.Money;

import java.time.LocalDate;

public class RoundupCalculatedEvent {
    private final LocalDate from;
    private Account account;

    public RoundupCalculatedEvent(LocalDate from, Account account) {
        this.from = from;
        this.account = account;
    }

    public LocalDate getFrom() {
        return from;
    }

    public Account getAccount() {
        return account;
    }

    public Money getRoundup() {
        return null;
    }
}
