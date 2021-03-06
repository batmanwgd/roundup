package dev.luke10x.starling.roundup.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
public class RoundupCommandTest {

    @Mock
    DateResolver dateResolver;
    @Mock
    RoundupCollector roundupCollector;

    @Test
    void resolvesDateFromPassedYearAndWeek() {
        RoundupCommand roundupCommand = new RoundupCommand(dateResolver, roundupCollector);

        roundupCommand.execute(2020, 22);

        verify(dateResolver).resolve(2020, 22 );
    }

    @Test
    void collectsRoundupStartingWithResolvedDate() {
        LocalDate resolvedFromDate = LocalDate.of(2004, Month.MARCH, 2);
        LocalDate resolvedToDate = LocalDate.of(2004, Month.MARCH, 9);
        LocalDate anotherDate = LocalDate.of(2005, Month.DECEMBER, 3);

        when(dateResolver.resolve(2020, 11)).thenReturn(resolvedFromDate);
        when(dateResolver.resolve(2020, 12)).thenReturn(resolvedToDate);

        RoundupCommand roundupCommand = new RoundupCommand(dateResolver, roundupCollector);

        roundupCommand.execute(2020, 11);

        verify(roundupCollector, never()).collectRoundup(anotherDate, resolvedToDate);
        verify(roundupCollector).collectRoundup(resolvedFromDate, resolvedToDate);
    }
}
