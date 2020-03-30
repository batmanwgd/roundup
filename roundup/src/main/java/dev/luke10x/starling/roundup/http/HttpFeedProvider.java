package dev.luke10x.starling.roundup.http;

import dev.luke10x.starling.roundup.domain.FeedProvider;
import dev.luke10x.starling.roundup.domain.accounts.Account;
import dev.luke10x.starling.roundup.domain.feed.FeedItem;
import dev.luke10x.starling.roundup.domain.feed.FeedNotFoundException;
import dev.luke10x.starling.roundup.domain.feed.FeedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class HttpFeedProvider implements FeedProvider {

    private static Logger LOG = LoggerFactory
            .getLogger(HttpFeedProvider.class);

    private RestTemplate restTemplate;
    private String starlingHost;

    public HttpFeedProvider(
            RestTemplate restTemplate,
            @Value("${roundup.starling_host}") String starlingHost
    ) {
        this.restTemplate = restTemplate;
        this.starlingHost = starlingHost;
    }
    @Override public List<FeedItem> fetch(Account account, LocalDate from, LocalDate to) throws FeedNotFoundException {

        LOG.info("\uD83D\uDCC6 FETCHING FEED: "+from.toString()+" - "+to.toString());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        String url = starlingHost
                + "/api/v2/feed/account/" + account.getAccountUid()
                + "/category/" + account.getDefaultCategory()
                + "?changesSince=" + from.atStartOfDay(ZoneId.of("UTC")).format(formatter);

        try {
            return Objects.requireNonNull(restTemplate
                    .getForObject(url, FeedResponse.class))
                    .getFeedItems()
                    .stream()
                    .filter(
                        item
                                -> item.getTransactionTime().isAfter(from.atStartOfDay(ZoneId.of("UTC")))
                                && item.getTransactionTime().isBefore(to.atStartOfDay(ZoneId.of("UTC")))
                    )
                    .collect(Collectors.toList());
        } catch (HttpClientErrorException ex) {
            throw new FeedNotFoundException();
        }
    }
}
