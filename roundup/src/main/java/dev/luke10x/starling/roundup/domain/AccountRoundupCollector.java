package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.feed.Money;
import dev.luke10x.starling.roundup.domain.feed.TransactionFeed;

import java.time.LocalDate;

public class AccountRoundupCollector {

    private final TransactionFeedProvider transactionFeedProvider;
    private final TransactionFeedCalculator transactionFeedCalculator;
    
    public AccountRoundupCollector(
            TransactionFeedProvider transactionFeedProvider,
            TransactionFeedCalculator transactionFeedCalculator
    ) {
        this.transactionFeedProvider = transactionFeedProvider;
        this.transactionFeedCalculator = transactionFeedCalculator;
    }

    public Money collectRoundup(LocalDate from) {
        TransactionFeed feed = transactionFeedProvider.fetch(from);
        return transactionFeedCalculator.calculate(feed);
    }
}
