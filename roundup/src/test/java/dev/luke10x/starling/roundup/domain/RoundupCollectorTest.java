package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.accounts.Account;
import dev.luke10x.starling.roundup.domain.accounts.AccountsResponse;
import dev.luke10x.starling.roundup.domain.feed.FeedItem;
import dev.luke10x.starling.roundup.domain.feed.FeedNotFoundException;
import dev.luke10x.starling.roundup.domain.feed.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class RoundupCollectorTest {

    @Mock
    FeedProvider feedProvider;

    @Mock
    FeedCalculator feedCalculator;

    @Mock
    AccountsProvider accountsProvider;

    @Mock
    RoundupCalculatedEventListener roundupCalculatedEventListener;

    @Test
    void fetchesFeedByItsStartingDateUsingProvidedAccount() throws FeedNotFoundException {
        Account account = new Account(
                "ac82f660-5442-4b78-9038-2b72b1206390",
                "2eb42e49-f275-4019-8707-81a0637e7206"
        );
        AccountsResponse accountsResponse = buildAccountsResponse(account);
        when(accountsProvider.fetch()).thenReturn(accountsResponse);
        LocalDate startingDate = LocalDate.of(2020, Month.JANUARY, 10);
        LocalDate tillDate = LocalDate.of(2020, Month.JANUARY, 10);
        LocalDate anotherDate = LocalDate.of(2020, Month.FEBRUARY, 20);
        RoundupCollector roundupCollector = new RoundupCollector(
                accountsProvider,
                feedProvider,
                feedCalculator,
                roundupCalculatedEventListener);

        roundupCollector.collectRoundup(startingDate, startingDate);

        verify(feedProvider, never()).fetch(account, anotherDate, tillDate);
        verify(feedProvider).fetch(account, startingDate, tillDate);
    }

    private AccountsResponse buildAccountsResponse(Account account) {
        return new AccountsResponse(Arrays.asList(account));
    }

    @Test
    void calculatesAmountFromFetchedFeed() throws FeedNotFoundException {

        Account account = new Account(
                "ac82f660-5442-4b78-9038-2b72b1206390",
                "2eb42e49-f275-4019-8707-81a0637e7206"
        );
        AccountsResponse accountsResponse = buildAccountsResponse(account);
        when(accountsProvider.fetch()).thenReturn(accountsResponse);

        List<FeedItem> fetchedFeedItems = List.of(new FeedItem());
        List<FeedItem> anotherFeedItems = List.of(new FeedItem());

        when(feedProvider.fetch(any(), any(), any())).thenReturn(fetchedFeedItems);

        RoundupCollector roundupCollector = new RoundupCollector(
                accountsProvider, feedProvider,
                feedCalculator,
                roundupCalculatedEventListener);
        LocalDate from = LocalDate.of(2020, Month.MARCH, 15);

        roundupCollector.collectRoundup(from, from);

        verify(feedCalculator, never()).calculate(anotherFeedItems);
        verify(feedCalculator).calculate(fetchedFeedItems);
    }

    @Test
    void dispatchesEventWithCalculatedAmount() {
        Account account = new Account(
                "ac82f660-5442-4b78-9038-2b72b1206390",
                "2eb42e49-f275-4019-8707-81a0637e7206"
        );
        AccountsResponse accountsResponse = buildAccountsResponse(account);
        when(accountsProvider.fetch()).thenReturn(accountsResponse);

        Money roundup = new Money("GBP", 1000);
        when(feedCalculator.calculate(any())).thenReturn(roundup);
        RoundupCollector roundupCollector = new RoundupCollector(
                accountsProvider,
                feedProvider,
                feedCalculator,
                roundupCalculatedEventListener
        );
        LocalDate from = LocalDate.of(2020, Month.MARCH, 15);

        roundupCollector.collectRoundup(from, from);

        verify(roundupCalculatedEventListener).onRoundupCalculated(argThat(
                event -> event.getFrom().equals(from)
        ));

        verify(roundupCalculatedEventListener).onRoundupCalculated(argThat(
                event -> event.getAccount().equals(account)
        ));

        verify(roundupCalculatedEventListener).onRoundupCalculated(argThat(
                event -> event.getRoundup().equals(roundup)
        ));
    }
}
