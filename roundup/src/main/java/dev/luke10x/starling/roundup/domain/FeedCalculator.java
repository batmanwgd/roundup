package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.feed.FeedItem;
import dev.luke10x.starling.roundup.domain.feed.Money;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FeedCalculator {
    public Money calculate(List<FeedItem> feedItems) {
        Money feedRoundup = feedItems.stream()
                .map(item -> item.getAmount())
                .reduce(
                        new Money("GBP", 0),
                        (subtotal, item) -> subtotal.add(item.roundup())
                );
        return feedRoundup;
    }
}
