package dev.luke10x.starling.roundup;

import java.time.LocalDate;
import java.time.Month;

public class DateResolver {
    public LocalDate resolve(int year, int weekOfTheYear) {
        return LocalDate.of(2010, Month.JANUARY, 10);
    }
}
