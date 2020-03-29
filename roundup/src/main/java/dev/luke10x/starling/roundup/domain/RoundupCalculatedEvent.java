package dev.luke10x.starling.roundup.domain;

import java.time.LocalDate;

public class RoundupCalculatedEvent {
    private final LocalDate from;

    public RoundupCalculatedEvent(LocalDate from) {
        this.from = from;
    }

    public LocalDate getFrom() {
        return from;
    }
}
