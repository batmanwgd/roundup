package dev.luke10x.starling.roundup;

import dev.luke10x.starling.roundup.feed.Money;
import dev.luke10x.starling.roundup.feed.TransactionFeed;

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
