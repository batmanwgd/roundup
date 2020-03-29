package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.feed.FeedItem;
import dev.luke10x.starling.roundup.domain.feed.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class FeedCalculatorTest {

    @Test
    void sumsUpRoundupOfAllFeedItems() {

        FeedCalculator feedCalculator = new FeedCalculator();

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

        Money roundup = feedCalculator.calculate(feedItems);

        assertEquals("GBP", roundup.getCurrency());
        assertEquals(158, roundup.getMinorUnits());
    }
}
