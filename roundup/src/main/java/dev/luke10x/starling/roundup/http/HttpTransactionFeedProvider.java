package dev.luke10x.starling.roundup.http;

import dev.luke10x.starling.roundup.domain.TransactionFeedProvider;
import dev.luke10x.starling.roundup.domain.accounts.Account;
import dev.luke10x.starling.roundup.domain.feed.FeedNotFoundException;
import dev.luke10x.starling.roundup.domain.feed.TransactionFeed;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Component
public class HttpTransactionFeedProvider implements TransactionFeedProvider {

    private RestTemplate restTemplate;
    private String starlingHost;

    public HttpTransactionFeedProvider(
            RestTemplate restTemplate,
            @Value("${roundup.starling_host}") String starlingHost
    ) {
        this.restTemplate = restTemplate;
        this.starlingHost = starlingHost;
    }

    @Override public TransactionFeed fetch(Account account, LocalDate from) throws FeedNotFoundException {

        String url = starlingHost
                + "/api/v2/feed/account/" + account.getAccountUid()
                + "/category/" + account.getDefaultCategory();

        try {
            return restTemplate.getForObject(url, TransactionFeed.class );
        } catch (HttpClientErrorException ex) {
            throw new FeedNotFoundException();
        }
    }
}
