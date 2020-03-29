package dev.luke10x.starling.roundup.domain.accounts;

public class Account {
    private String accountUid;
    private String defaultCategory;

    public Account() {}

    public Account(String accountUid, String defaultCategory) {
        this.accountUid = accountUid;
        this.defaultCategory = defaultCategory;
    }

    public String getAccountUid() {
        return accountUid;
    }

    public String getDefaultCategory() {
        return defaultCategory;
    }
}
