package dev.luke10x.starling.roundup.backend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

        weeklyRoundupCollector.saveRoundup(2020, 11);

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

        Money roundup = weeklyRoundupCollector.saveRoundup(2020, 11);

        assertEquals("GBP", roundup.getCurrecy());
        assertEquals(1000, roundup.getMinorUnits());
    }
}
