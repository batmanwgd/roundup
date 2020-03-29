package dev.luke10x.starling.roundup;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
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
