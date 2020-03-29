package dev.luke10x.starling.roundup.domain;

import dev.luke10x.starling.roundup.domain.accounts.AccountsResponse;

public interface AccountsProvider {
    AccountsResponse fetch();
}
