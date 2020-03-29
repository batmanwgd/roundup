package dev.luke10x.starling.roundup.domain.feed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FeedResponse {

    private List<FeedItem> feedItems = new LinkedList<>();

    public FeedResponse() { }

    public List<FeedItem> getFeedItems() {
        return feedItems;
    }
}
