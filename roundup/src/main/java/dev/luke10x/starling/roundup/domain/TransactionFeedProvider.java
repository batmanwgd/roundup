package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.accounts.Account;
import dev.luke10x.starling.roundup.domain.feed.TransactionFeed;

import java.time.LocalDate;

public interface TransactionFeedProvider {
    TransactionFeed fetch(Account account, LocalDate from);
}
