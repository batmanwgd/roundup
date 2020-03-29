package dev.luke10x.starling.roundup.backend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class WeeklyRoundupCollectorTest {

    @Mock
    TransactionFeedCalculator transactionFeedCalculator;

    @Test
    void returnsCalculatedAmount() {

        when(transactionFeedCalculator.calculate(any())).thenReturn(new Money("GBP", 1000));
        WeeklyRoundupCollector weeklyRoundupCollector = new WeeklyRoundupCollector(transactionFeedCalculator);

        Money roundup = weeklyRoundupCollector.saveRoundup(2020, 11);

        assertEquals("GBP", roundup.getCurrecy());
        assertEquals(1000, roundup.getMinorUnits());
    }
}
