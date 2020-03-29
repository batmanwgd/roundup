package dev.luke10x.starling.roundup.http;

import dev.luke10x.starling.roundup.TransactionFeedProvider;
import dev.luke10x.starling.roundup.feed.TransactionFeed;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

public class HttpTransactionFeedProvider implements TransactionFeedProvider {

    private RestTemplate restTemplate;
    private String starlingHost;

    public HttpTransactionFeedProvider(RestTemplate restTemplate, String starlingHost) {
        this.restTemplate = restTemplate;
        this.starlingHost = starlingHost;
    }

    @Override public TransactionFeed fetch(LocalDate from) {

        // TODO actually make call to accounts endpoint to obtain account ID and category ID
        String url = starlingHost +
        "/api/v2/feed/account/ac82f660-5442-4b78-9038-2b72b1206390/category/" +
                "2eb42e49-f275-4019-8707-81a0637e7206";

        return restTemplate.getForObject(url, TransactionFeed.class );
    }
}
