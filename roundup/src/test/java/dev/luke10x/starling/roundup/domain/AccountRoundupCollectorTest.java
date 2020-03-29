package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.accounts.Account;
import dev.luke10x.starling.roundup.domain.accounts.AccountsResponse;
import dev.luke10x.starling.roundup.domain.feed.FeedNotFoundException;
import dev.luke10x.starling.roundup.domain.feed.Money;
import dev.luke10x.starling.roundup.domain.feed.TransactionFeed;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class AccountRoundupCollectorTest {

    @Mock
    TransactionFeedProvider transactionFeedProvider;

    @Mock
    TransactionFeedCalculator transactionFeedCalculator;

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
        LocalDate anotherDate = LocalDate.of(2020, Month.FEBRUARY, 20);
        AccountRoundupCollector accountRoundupCollector = new AccountRoundupCollector(
                accountsProvider,
                transactionFeedProvider,
                transactionFeedCalculator,
                roundupCalculatedEventListener);

        accountRoundupCollector.collectRoundup(startingDate);

        verify(transactionFeedProvider, never()).fetch(account, anotherDate);
        verify(transactionFeedProvider).fetch(account, startingDate);
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

        TransactionFeed fetchedFeed = new TransactionFeed();
        TransactionFeed anotherFeed = new TransactionFeed();
        when(transactionFeedProvider.fetch(any(), any())).thenReturn(fetchedFeed);

        AccountRoundupCollector accountRoundupCollector = new AccountRoundupCollector(
                accountsProvider, transactionFeedProvider,
                transactionFeedCalculator,
                roundupCalculatedEventListener);
        LocalDate from = LocalDate.of(2020, Month.MARCH, 15);

        accountRoundupCollector.collectRoundup(from);

        verify(transactionFeedCalculator, never()).calculate(anotherFeed);
        verify(transactionFeedCalculator).calculate(fetchedFeed);
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
        when(transactionFeedCalculator.calculate(any())).thenReturn(roundup);
        AccountRoundupCollector accountRoundupCollector = new AccountRoundupCollector(
                accountsProvider,
                transactionFeedProvider,
                transactionFeedCalculator,
                roundupCalculatedEventListener
        );
        LocalDate from = LocalDate.of(2020, Month.MARCH, 15);

        accountRoundupCollector.collectRoundup(from);

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
