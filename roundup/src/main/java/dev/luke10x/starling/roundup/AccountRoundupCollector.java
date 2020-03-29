package dev.luke10x.starling.roundup;

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
        final long calendarWeek = 11;
        LocalDate desiredDate = LocalDate.of(2020, 1, 1)
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, calendarWeek)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        TransactionFeed feed = transactionFeedProvider.fetch(desiredDate);
        return transactionFeedCalculator.calculate(feed);
    }
}
