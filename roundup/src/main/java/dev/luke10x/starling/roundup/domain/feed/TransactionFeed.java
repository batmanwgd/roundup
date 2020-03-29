package dev.luke10x.starling.roundup.domain.feed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionFeed {

    private List<FeedItem> feedItems = new LinkedList<>();

    public TransactionFeed() { }

    public List<FeedItem> getFeedItems() {
        return feedItems;
    }
}
