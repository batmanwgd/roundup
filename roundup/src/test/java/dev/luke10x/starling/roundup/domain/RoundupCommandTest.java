package dev.luke10x.starling.roundup.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class RoundupCommandTest {

    @Mock
    DateResolver dateResolver;
    @Mock
    AccountRoundupCollector accountRoundupCollector;

    @Test
    void resolvesDateFromPassedYearAndWeek() {
        RoundupCommand roundupCommand = new RoundupCommand(dateResolver, accountRoundupCollector);

        roundupCommand.execute(2020, 22);

        verify(dateResolver).resolve(2020, 22 );
    }

    @Test
    void collectsRoundupStartingWithResolvedDate() {
        LocalDate resolvedFromDate = LocalDate.of(2004, Month.MARCH, 2);
        LocalDate resolvedToDate = LocalDate.of(2004, Month.MARCH, 2);
        LocalDate anotherDate = LocalDate.of(2005, Month.DECEMBER, 3);
        when(dateResolver.resolve(anyInt(), anyInt())).thenReturn(resolvedFromDate);
        RoundupCommand roundupCommand = new RoundupCommand(dateResolver, accountRoundupCollector);

        roundupCommand.execute(2020, 11);

        verify(accountRoundupCollector, never()).collectRoundup(anotherDate, resolvedToDate);
        verify(accountRoundupCollector).collectRoundup(resolvedFromDate, resolvedToDate);
    }
}
