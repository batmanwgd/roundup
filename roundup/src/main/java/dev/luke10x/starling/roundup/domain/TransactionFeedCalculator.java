package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.feed.Money;
import dev.luke10x.starling.roundup.domain.feed.TransactionFeed;

public class TransactionFeedCalculator {
    public Money calculate(TransactionFeed feed) {
        return new Money("GBP", 0);
    }
}
