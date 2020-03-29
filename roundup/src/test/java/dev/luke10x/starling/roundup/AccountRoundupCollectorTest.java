package dev.luke10x.starling.roundup;

import dev.luke10x.starling.roundup.feed.Money;
import dev.luke10x.starling.roundup.feed.TransactionFeed;
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
public class AccountRoundupCollectorTest {

    @Mock TransactionFeedProvider transactionFeedProvider;

    @Mock TransactionFeedCalculator transactionFeedCalculator;

    @Test
    void fetchesFeedByItsStartingDate() {
        LocalDate startingDate = LocalDate.of(2020, Month.JANUARY, 10);
        LocalDate anotherDate = LocalDate.of(2020, Month.FEBRUARY, 20);
        AccountRoundupCollector accountRoundupCollector = new AccountRoundupCollector(
                transactionFeedProvider,
                transactionFeedCalculator
        );

        accountRoundupCollector.collectRoundup(startingDate);

        verify(transactionFeedProvider, never()).fetch(anotherDate);
        verify(transactionFeedProvider).fetch(startingDate);
    }

    @Test
    void calculatesAmountFromFetchedFeed() {
        TransactionFeed fetchedFeed = new TransactionFeed();
        TransactionFeed anotherFeed = new TransactionFeed();
        when(transactionFeedProvider.fetch(any())).thenReturn(fetchedFeed);
        AccountRoundupCollector accountRoundupCollector = new AccountRoundupCollector(
                transactionFeedProvider,
                transactionFeedCalculator
        );
        LocalDate from = LocalDate.of(2020, Month.MARCH, 15);

        accountRoundupCollector.collectRoundup(from);

        verify(transactionFeedCalculator, never()).calculate(anotherFeed);
        verify(transactionFeedCalculator).calculate(fetchedFeed);
    }

    @Test
    void returnsCalculatedAmount() {

        when(transactionFeedCalculator.calculate(any())).thenReturn(new Money("GBP", 1000));
        AccountRoundupCollector accountRoundupCollector = new AccountRoundupCollector(
                transactionFeedProvider,
                transactionFeedCalculator
        );
        LocalDate from = LocalDate.of(2020, Month.MARCH, 15);

        Money roundup = accountRoundupCollector.collectRoundup(from);

        assertEquals("GBP", roundup.getCurrency());
        assertEquals(1000, roundup.getMinorUnits());
    }
}
