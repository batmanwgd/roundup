package dev.luke10x.starling.roundup.backend;

import java.time.LocalDate;

public class TransactionFeedProvider {
    public TransactionFeed fetch(LocalDate from) {
        return new TransactionFeed();
    }
}
