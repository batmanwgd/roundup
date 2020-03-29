package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.feed.FeedItem;
import dev.luke10x.starling.roundup.domain.feed.Money;
import dev.luke10x.starling.roundup.domain.feed.TransactionFeed;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class TransactionFeedCalculatorTest {

    @Test
    void sumsUpRoundupOfAllFeedItems() {

        TransactionFeedCalculator transactionFeedCalculator = new TransactionFeedCalculator();

        TransactionFeed feed = new TransactionFeed();
        feed.getFeedItems().add(new FeedItem(
                "OUT",
                new Money("GBP", 435)
        ));
        feed.getFeedItems().add(new FeedItem(
                "OUT",
                new Money("GBP", 520)
        ));
        feed.getFeedItems().add(new FeedItem(
                "OUT",
                new Money("GBP", 87)
        ));

        Money roundup = transactionFeedCalculator.calculate(feed);

        assertEquals("GBP", roundup.getCurrency());
        assertEquals(158, roundup.getMinorUnits());
    }
}
