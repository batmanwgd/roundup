package dev.luke10x.starling.roundup.domain.accounts;

import java.util.List;

public class AccountsResponse {
    private List<Account> accounts;

    public AccountsResponse() {}

    public <T> AccountsResponse(List<Account> accounts) {

        this.accounts = accounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
