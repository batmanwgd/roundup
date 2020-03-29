package dev.luke10x.starling.roundup.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.IsoFields;
import java.time.temporal.TemporalAdjusters;

public class DateResolver {
    public LocalDate resolve(int year, int weekOfTheYear) {
        return LocalDate.of(year, 1, 1)
                .with(IsoFields.WEEK_OF_WEEK_BASED_YEAR, weekOfTheYear)
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
    }
}
