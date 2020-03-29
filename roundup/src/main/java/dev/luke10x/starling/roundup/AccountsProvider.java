package dev.luke10x.starling.roundup;

import dev.luke10x.starling.roundup.accounts.AccountsResponse;
import org.springframework.web.client.RestTemplate;

public class AccountsProvider {
    private final RestTemplate restTemplate;
    private final String starlingHost;

    public AccountsProvider(RestTemplate restTemplate, String starlingHost) {

        this.restTemplate = restTemplate;
        this.starlingHost = starlingHost;
    }

    public AccountsResponse fetch() {
        return null;
    }
}
