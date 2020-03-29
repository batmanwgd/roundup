package dev.luke10x.starling.roundup.domain;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RoundupCommand {
    private DateResolver dateResolver;
    private RoundupCollector roundupCollector;

    public RoundupCommand(
            DateResolver dateResolver,
            RoundupCollector roundupCollector
    ) {
        this.dateResolver = dateResolver;
        this.roundupCollector = roundupCollector;
    }

    public void execute(int year, int weekOfTheYear) {
        LocalDate from = dateResolver.resolve(year, weekOfTheYear);
        LocalDate to = dateResolver.resolve(year, weekOfTheYear+1);


        roundupCollector.collectRoundup(from, to);
    }
}
