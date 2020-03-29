package dev.luke10x.starling.roundup.domain.feed;

import java.time.ZonedDateTime;

public class FeedItem {

    private String direction;
    private Money amount;
    private ZonedDateTime transactionTime;

    public FeedItem() {}

    public FeedItem(String direction, Money amount) {

        this.direction = direction;
        this.amount = amount;
    }

    public String getDirection() {
        return direction;
    }

    public Money getAmount() {
        return amount;
    }

    public ZonedDateTime getTransactionTime() {
        return transactionTime;
    }
}
