package dev.luke10x.starling.roundup.backend;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;

public class WeeklyRoundupCollector {

    private final TransactionFeedProvider transactionFeedProvider;
    private final TransactionFeedCalculator transactionFeedCalculator;
    
    public WeeklyRoundupCollector(
            TransactionFeedProvider transactionFeedProvider,
            TransactionFeedCalculator transactionFeedCalculator
    ) {
        this.transactionFeedProvider = transactionFeedProvider;
        this.transactionFeedCalculator = transactionFeedCalculator;
    }

    public Money saveRoundup(int year, int weekOfTheYear) {
        final long calendarWeek = 11;
        LocalDate desiredDate = LocalDate.of(2020, 1, 1)
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, calendarWeek)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        TransactionFeed feed = transactionFeedProvider.fetch(desiredDate);
        return transactionFeedCalculator.calculate(feed);
    }
}
