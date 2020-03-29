package dev.luke10x.starling.roundup.domain.feed;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith({MockitoExtension.class})
public class MoneyTest {

    @Test
    void twoMoneysOfSameCurrencyAddUp() {
        Money money1 = new Money("GBP", 111);
        Money money2 = new Money("GBP", 222);

        Money money3 = money1.add(money2);

        assertEquals(333, money3.getMinorUnits());
        assertEquals("GBP", money3.getCurrency());
    }

    @Test
    void calculatesRoundupOfSingleMoneySmallValue() {
        Money money = new Money("GBP", 2);

        Money roundup = money.roundup();

        assertEquals(98, roundup.getMinorUnits());
        assertEquals("GBP", roundup.getCurrency());
    }

    @Test
    void calculatesRoundupOfSingleMoney() {
        Money money = new Money("GBP", 777);

        Money roundup = money.roundup();

        assertEquals(23, roundup.getMinorUnits());
        assertEquals("GBP", roundup.getCurrency());
    }

    @Test
    void printsNicely() {
        Money hundredDollars = new Money("USD", 10000);

        assertEquals("USD 100.00", hundredDollars.toString());
    }
}
