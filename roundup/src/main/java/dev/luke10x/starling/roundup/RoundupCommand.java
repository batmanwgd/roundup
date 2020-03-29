package dev.luke10x.starling.roundup;

import java.time.LocalDate;

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
        accountRoundupCollector.collectRoundup(from);
    }
}
