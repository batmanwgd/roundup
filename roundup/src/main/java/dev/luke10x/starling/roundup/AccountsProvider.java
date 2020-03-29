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
        String url = starlingHost + "/api/v2/accounts";

        return restTemplate.getForObject(url, AccountsResponse.class );
    }
}
