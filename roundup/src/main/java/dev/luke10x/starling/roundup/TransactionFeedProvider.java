package dev.luke10x.starling.roundup.backend;

import java.time.Period;

public class TransactionFeedProvider {
    public TransactionFeed fetch(Period period) {
        return new TransactionFeed();
    }
}
