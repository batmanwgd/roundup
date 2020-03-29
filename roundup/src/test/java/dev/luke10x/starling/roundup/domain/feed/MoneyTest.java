package dev.luke10x.starling.roundup.domain.feed;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

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
}
