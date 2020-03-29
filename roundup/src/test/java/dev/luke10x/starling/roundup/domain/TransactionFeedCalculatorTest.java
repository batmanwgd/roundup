package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.feed.FeedItem;
import dev.luke10x.starling.roundup.domain.feed.Money;
import dev.luke10x.starling.roundup.domain.feed.TransactionFeed;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class TransactionFeedCalculatorTest {

    @Test
    void sumsUpRoundupOfAllFeedItems() {

        TransactionFeedCalculator transactionFeedCalculator = new TransactionFeedCalculator();

        List<FeedItem> feedItems = new LinkedList<>();
        feedItems.add(new FeedItem(
                "OUT",
                new Money("GBP", 435)
        ));
        feedItems.add(new FeedItem(
                "OUT",
                new Money("GBP", 520)
        ));
        feedItems.add(new FeedItem(
                "OUT",
                new Money("GBP", 87)
        ));

        Money roundup = transactionFeedCalculator.calculate(feedItems);

        assertEquals("GBP", roundup.getCurrency());
        assertEquals(158, roundup.getMinorUnits());
    }
}
