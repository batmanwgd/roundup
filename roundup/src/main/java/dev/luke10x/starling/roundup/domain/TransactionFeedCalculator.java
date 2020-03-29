package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.feed.Money;
import dev.luke10x.starling.roundup.domain.feed.TransactionFeed;
import org.springframework.stereotype.Component;

@Component
public class TransactionFeedCalculator {
    public Money calculate(TransactionFeed feed) {
        Money feedRoundup = feed.getFeedItems().stream()
                .map(item -> item.getAmount())
                .reduce(
                        new Money("GBP", 0),
                        (subtotal, item) -> subtotal.add(item.roundup())
                );
        return feedRoundup;
    }
}
