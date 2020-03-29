package dev.luke10x.starling.roundup;

import dev.luke10x.starling.roundup.feed.Money;
import dev.luke10x.starling.roundup.feed.TransactionFeed;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;

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
