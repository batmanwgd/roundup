package dev.luke10x.starling.roundup;

import dev.luke10x.starling.roundup.accounts.AccountsResponse;

public interface AccountsProvider {
    AccountsResponse fetch();
}
