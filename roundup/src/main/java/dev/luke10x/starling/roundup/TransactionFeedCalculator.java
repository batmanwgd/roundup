package dev.luke10x.starling.roundup;

import dev.luke10x.starling.roundup.feed.Money;
import dev.luke10x.starling.roundup.feed.TransactionFeed;

public class TransactionFeedCalculator {
    public Money calculate(TransactionFeed feed) {
        return new Money("GBP", 0);
    }
}
