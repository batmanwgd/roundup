package dev.luke10x.starling.roundup.domain;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RoundupCommand {
    private DateResolver dateResolver;
    private AccountRoundupCollector accountRoundupCollector;

    public RoundupCommand(
            DateResolver dateResolver,
            AccountRoundupCollector accountRoundupCollector
    ) {
        this.dateResolver = dateResolver;
        this.accountRoundupCollector = accountRoundupCollector;
    }

    public void execute(int year, int weekOfTheYear) {
        LocalDate from = dateResolver.resolve(year, weekOfTheYear);
        LocalDate to = dateResolver.resolve(year, weekOfTheYear+1);


        accountRoundupCollector.collectRoundup(from, to);
    }
}
