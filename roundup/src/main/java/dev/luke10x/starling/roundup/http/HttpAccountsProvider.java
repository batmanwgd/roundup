package dev.luke10x.starling.roundup.http;

import dev.luke10x.starling.roundup.AccountsProvider;
import dev.luke10x.starling.roundup.accounts.AccountsResponse;
import org.springframework.web.client.RestTemplate;

public class HttpAccountsProvider implements AccountsProvider {
    private final RestTemplate restTemplate;
    private final String starlingHost;

    public HttpAccountsProvider(RestTemplate restTemplate, String starlingHost) {

        this.restTemplate = restTemplate;
        this.starlingHost = starlingHost;
    }

    @Override public AccountsResponse fetch() {
        String url = starlingHost + "/api/v2/accounts";

        return restTemplate.getForObject(url, AccountsResponse.class );
    }
}
