package dev.luke10x.starling.roundup.backend;

public class WeeklyRoundupCollector {
    
    private final TransactionFeedCalculator transactionFeedCalculator;
    
    public WeeklyRoundupCollector(TransactionFeedCalculator transactionFeedCalculator) {
        this.transactionFeedCalculator = transactionFeedCalculator;
    }

    public Money saveRoundup(int year, int weekOfTheYear) {
        TransactionFeed feed = new TransactionFeed();
        return transactionFeedCalculator.calculate(feed);
    }
}
