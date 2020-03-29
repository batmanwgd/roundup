package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.accounts.Account;
import dev.luke10x.starling.roundup.domain.feed.FeedNotFoundException;
import dev.luke10x.starling.roundup.domain.feed.Money;
import dev.luke10x.starling.roundup.domain.feed.TransactionFeed;

import java.time.LocalDate;

public class AccountRoundupCollector {

    private AccountsProvider accountsProvider;
    private final TransactionFeedProvider transactionFeedProvider;
    private final TransactionFeedCalculator transactionFeedCalculator;
    private RoundupCalculatedEventListener roundupCalculatedEventListener;

    public AccountRoundupCollector(
            AccountsProvider accountsProvider, TransactionFeedProvider transactionFeedProvider,
            TransactionFeedCalculator transactionFeedCalculator,
            RoundupCalculatedEventListener roundupCalculatedEventListener) {
        this.accountsProvider = accountsProvider;
        this.transactionFeedProvider = transactionFeedProvider;
        this.transactionFeedCalculator = transactionFeedCalculator;
        this.roundupCalculatedEventListener = roundupCalculatedEventListener;
    }

    public void collectRoundup(LocalDate from) {
        Account account = accountsProvider.fetch().getAccounts().get(0);
        try {
            TransactionFeed feed = transactionFeedProvider.fetch(account, from);
            transactionFeedCalculator.calculate(feed);

            RoundupCalculatedEvent event = new RoundupCalculatedEvent(from);
            roundupCalculatedEventListener.onRoundupCalculated(event);
        } catch (FeedNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
