package dev.luke10x.starling.roundup;

import dev.luke10x.starling.roundup.feed.TransactionFeed;

import java.time.LocalDate;

public interface TransactionFeedProvider {
    TransactionFeed fetch(LocalDate from);
}
