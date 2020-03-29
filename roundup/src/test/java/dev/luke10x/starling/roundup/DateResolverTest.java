package dev.luke10x.starling.roundup;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateResolverTest {

    /*
     *    December 2019
     * Su Mo Tu We Th Fr Sa
     *  1  2  3  4  5  6  7
     *  8  9 10 11 12 13 14
     * 15 16 17 18 19 20 21
     * 22 23 24 25 26 27 28
     * 29 30 31
     *
     *     January 2020
     * Su Mo Tu We Th Fr Sa
     *           1  2  3  4
     *  5  6  7  8  9 10 11
     * 12 13 14 15 16 17 18
     * 19 20 21 22 23 24 25
     * 26 27 28 29 30 31
     */

    @Test
    void firstWeekOf2020StartsOnPreviousYear30OfDecember() {
        DateResolver dateResolver = new DateResolver();

        LocalDate date = dateResolver.resolve(2020, 1);

        assertEquals(2019, date.getYear());
        assertEquals(Month.DECEMBER, date.getMonth());
        assertEquals(30, date.getDayOfMonth());
        assertEquals(DayOfWeek.MONDAY, date.getDayOfWeek());
    }

    @Test
    @Disabled
    void secondWeekOf2020StartsOnThisYear6OfJanuary() {
        DateResolver dateResolver = new DateResolver();

        LocalDate date = dateResolver.resolve(2020, 2);

        assertEquals(2020, date.getYear());
        assertEquals(Month.JANUARY, date.getMonth());
        assertEquals(6, date.getDayOfMonth());
        assertEquals(DayOfWeek.MONDAY, date.getDayOfWeek());
    }
}
