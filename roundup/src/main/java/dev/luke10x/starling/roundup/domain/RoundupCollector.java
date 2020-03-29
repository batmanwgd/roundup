package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.accounts.Account;
import dev.luke10x.starling.roundup.domain.feed.FeedItem;
import dev.luke10x.starling.roundup.domain.feed.FeedNotFoundException;
import dev.luke10x.starling.roundup.domain.feed.Money;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class RoundupCollector {

    private AccountsProvider accountsProvider;
    private final FeedProvider feedProvider;
    private final FeedCalculator feedCalculator;
    private RoundupCalculatedEventListener roundupCalculatedEventListener;

    public RoundupCollector(
            AccountsProvider accountsProvider, FeedProvider feedProvider,
            FeedCalculator feedCalculator,
            RoundupCalculatedEventListener roundupCalculatedEventListener) {
        this.accountsProvider = accountsProvider;
        this.feedProvider = feedProvider;
        this.feedCalculator = feedCalculator;
        this.roundupCalculatedEventListener = roundupCalculatedEventListener;
    }

    public void collectRoundup(LocalDate from, LocalDate to) {
        Account account = accountsProvider.fetch().getAccounts().get(0);
        try {
            List<FeedItem> feedItems = feedProvider.fetch(account, from, to);
            Money roundup = feedCalculator.calculate(feedItems);

            RoundupCalculatedEvent event = new RoundupCalculatedEvent(from, account, roundup);
            roundupCalculatedEventListener.onRoundupCalculated(event);
        } catch (FeedNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
