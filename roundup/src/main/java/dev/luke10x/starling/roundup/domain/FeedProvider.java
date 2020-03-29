package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.accounts.Account;
import dev.luke10x.starling.roundup.domain.feed.FeedItem;
import dev.luke10x.starling.roundup.domain.feed.FeedNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface FeedProvider {
    List<FeedItem> fetch(Account account, LocalDate from, LocalDate to) throws FeedNotFoundException;
}
