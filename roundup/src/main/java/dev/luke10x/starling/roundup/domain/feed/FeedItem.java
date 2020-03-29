package dev.luke10x.starling.roundup.domain.feed;

public class FeedItem {

    private String direction;
    private Money amount;

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
}
