package dev.luke10x.starling.roundup.http;

import dev.luke10x.starling.roundup.domain.AccountsProvider;
import dev.luke10x.starling.roundup.domain.accounts.AccountsResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpAccountsProvider implements AccountsProvider {
    private final RestTemplate restTemplate;
    private final String starlingHost;

    public HttpAccountsProvider(
            RestTemplate restTemplate,
            @Value("${roundup.starling_host}") String starlingHost
    ) {
        this.restTemplate = restTemplate;
        this.starlingHost = starlingHost;
    }

    @Override public AccountsResponse fetch() {
        String url = starlingHost + "/api/v2/accounts";

        return restTemplate.getForObject(url, AccountsResponse.class );
    }
}
