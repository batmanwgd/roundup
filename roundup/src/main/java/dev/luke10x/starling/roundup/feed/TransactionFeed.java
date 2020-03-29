package dev.luke10x.starling.roundup.feed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.luke10x.starling.roundup.feed.FeedItem;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionFeed {

    private List<FeedItem> feedItems;
    public TransactionFeed() {
    }

    public List<FeedItem> getFeedItems() {
        return feedItems;
    }
}
