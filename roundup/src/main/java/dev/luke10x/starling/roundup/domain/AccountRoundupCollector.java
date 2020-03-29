package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.accounts.Account;
import dev.luke10x.starling.roundup.domain.feed.Money;
import dev.luke10x.starling.roundup.domain.feed.TransactionFeed;

import java.time.LocalDate;

public class AccountRoundupCollector {

    private AccountsProvider accountsProvider;
    private final TransactionFeedProvider transactionFeedProvider;
    private final TransactionFeedCalculator transactionFeedCalculator;
    
    public AccountRoundupCollector(
            AccountsProvider accountsProvider, TransactionFeedProvider transactionFeedProvider,
            TransactionFeedCalculator transactionFeedCalculator
    ) {
        this.accountsProvider = accountsProvider;
        this.transactionFeedProvider = transactionFeedProvider;
        this.transactionFeedCalculator = transactionFeedCalculator;
    }

    public Money collectRoundup(LocalDate from) {
        Account account = accountsProvider.fetch().getAccounts().get(0);
        TransactionFeed feed = transactionFeedProvider.fetch(account, from);
        return transactionFeedCalculator.calculate(feed);
    }
}
