package dev.luke10x.starling.roundup.backend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class WeeklyRoundupCollectorTest {

    @Mock TransactionFeedProvider transactionFeedProvider;

    @Mock TransactionFeedCalculator transactionFeedCalculator;

    @Test
    void calculatesAmountFromFetchedFeed() {
        TransactionFeed fetchedFeed = new TransactionFeed();
        TransactionFeed anotherFeed = new TransactionFeed();
        when(transactionFeedProvider.fetch(any())).thenReturn(fetchedFeed);
        WeeklyRoundupCollector weeklyRoundupCollector = new WeeklyRoundupCollector(
                transactionFeedProvider,
                transactionFeedCalculator
        );
        LocalDate from = LocalDate.of(2020, Month.MARCH, 15);

        weeklyRoundupCollector.collectRoundup(from);

        verify(transactionFeedCalculator, never()).calculate(anotherFeed);
        verify(transactionFeedCalculator).calculate(fetchedFeed);
    }

    @Test
    void returnsCalculatedAmount() {

        when(transactionFeedCalculator.calculate(any())).thenReturn(new Money("GBP", 1000));
        WeeklyRoundupCollector weeklyRoundupCollector = new WeeklyRoundupCollector(
                transactionFeedProvider,
                transactionFeedCalculator
        );
        LocalDate from = LocalDate.of(2020, Month.MARCH, 15);
        
        Money roundup = weeklyRoundupCollector.collectRoundup(from);

        assertEquals("GBP", roundup.getCurrecy());
        assertEquals(1000, roundup.getMinorUnits());
    }
}
