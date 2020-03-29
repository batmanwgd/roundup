package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.accounts.Account;
import dev.luke10x.starling.roundup.domain.feed.FeedItem;
import dev.luke10x.starling.roundup.domain.feed.FeedNotFoundException;
import dev.luke10x.starling.roundup.domain.feed.Money;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class AccountRoundupCollector {

    private AccountsProvider accountsProvider;
    private final TransactionFeedProvider transactionFeedProvider;
    private final TransactionFeedCalculator transactionFeedCalculator;
    private RoundupCalculatedEventListener roundupCalculatedEventListener;

    public AccountRoundupCollector(
            AccountsProvider accountsProvider, TransactionFeedProvider transactionFeedProvider,
            TransactionFeedCalculator transactionFeedCalculator,
            RoundupCalculatedEventListener roundupCalculatedEventListener) {
        this.accountsProvider = accountsProvider;
        this.transactionFeedProvider = transactionFeedProvider;
        this.transactionFeedCalculator = transactionFeedCalculator;
        this.roundupCalculatedEventListener = roundupCalculatedEventListener;
    }

    public void collectRoundup(LocalDate from, LocalDate to) {
        Account account = accountsProvider.fetch().getAccounts().get(0);
        try {
            List<FeedItem> feedItems = transactionFeedProvider.fetch(account, from, to);
            Money roundup = transactionFeedCalculator.calculate(feedItems);

            RoundupCalculatedEvent event = new RoundupCalculatedEvent(from, account, roundup);
            roundupCalculatedEventListener.onRoundupCalculated(event);
        } catch (FeedNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
