package dev.luke10x.starling.roundup.http;

import dev.luke10x.starling.roundup.RoundupApplication;
import dev.luke10x.starling.roundup.domain.FeedProvider;
import dev.luke10x.starling.roundup.domain.accounts.Account;
import dev.luke10x.starling.roundup.domain.feed.FeedItem;
import dev.luke10x.starling.roundup.domain.feed.FeedNotFoundException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZonedDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = { RoundupApplication.class })
@EnableConfigurationProperties
@TestPropertySource(locations="classpath:test.properties")
@ActiveProfiles("test")
public class HttpFeedProviderIntegrationTest {

    static private ClientAndServer starlingAPI;

    @BeforeAll
    public void startStub() throws IOException {
        starlingAPI = ClientAndServer.startClientAndServer(0);

        HttpRequest request = HttpRequest.request()
                .withMethod("GET")
                .withPath("/api/v2/feed/account/ac82f660-5442-4b78-9038-2b72b1206390/category/" +
                        "2eb42e49-f275-4019-8707-81a0637e7206")
                .withHeader("Authorization", "Bearer Test-Valid-Access-Token");


        File file = new File(getClass().getClassLoader().getResource("./feed-response.json").getPath());
        byte[] fixture = Files.readAllBytes(file.toPath());
        HttpResponse response = HttpResponse.response()
                .withStatusCode(200)
                .withHeader("Content-Type", "application/json")
                .withBody(fixture);

        starlingAPI.when(request).respond(response);
    }

    @AfterAll
    public void stopStub() {
        starlingAPI.stop();
    }

    @Autowired
    RestTemplate restTemplate;

    @Test
    void fetchesFeedFromHttp() throws FeedNotFoundException {

        String starlingHost = "http://localhost:" + starlingAPI.getPort();

        FeedProvider feedProvider = new HttpFeedProvider(restTemplate, starlingHost);

        Account account = new Account(
                "ac82f660-5442-4b78-9038-2b72b1206390",
                "2eb42e49-f275-4019-8707-81a0637e7206"
        );
        LocalDate from = LocalDate.of(2020, Month.MARCH, 16);
        LocalDate to = LocalDate.of(2020, Month.MARCH, 23);

        List<FeedItem> feedItems = feedProvider.fetch(account, from, to);

        assertNotNull(feedItems);
        assertTrue(feedItems.size() > 0);

        FeedItem first = feedItems.get(0);
        assertEquals("OUT", first.getDirection());
        assertEquals(3909, first.getAmount().getMinorUnits());
        assertEquals("GBP", first.getAmount().getCurrency());
        assertEquals( ZonedDateTime.parse("2020-03-22T17:02:52.803Z[UTC]"), first.getTransactionTime());
    }

    @Test
    void fetchesFeedFromHttpForDataRangeWithNoTransations() throws FeedNotFoundException {

        String starlingHost = "http://localhost:" + starlingAPI.getPort();

        FeedProvider feedProvider = new HttpFeedProvider(restTemplate, starlingHost);

        Account account = new Account(
                "ac82f660-5442-4b78-9038-2b72b1206390",
                "2eb42e49-f275-4019-8707-81a0637e7206"
        );
        LocalDate from = LocalDate.of(2020, Month.MARCH, 9);
        LocalDate to = LocalDate.of(2020, Month.MARCH, 16);

        List<FeedItem> feedItems = feedProvider.fetch(account, from, to);

        assertNotNull(feedItems);
        assertTrue(feedItems.size() == 0);
    }

    @Test
    void failFetchingFeedForNotExistingAccount() {

        String starlingHost = "http://localhost:" + starlingAPI.getPort();

        FeedProvider feedProvider = new HttpFeedProvider(restTemplate, starlingHost);

        Account account = new Account(
                "badAccountUID",
                "2eb42e49-f275-4019-8707-81a0637e7206"
        );
        LocalDate from = LocalDate.of(2020, Month.MARCH, 16);
        LocalDate to = LocalDate.of(2020, Month.MARCH, 23);

        Exception exception = assertThrows(Exception.class, () -> {
            feedProvider.fetch(account, from, to);
        });

        assertThat(exception, instanceOf(FeedNotFoundException.class));
    }

    @Test
    void failFetchingFeedForNotExistingDefaultCategory() {

        String starlingHost = "http://localhost:" + starlingAPI.getPort();

        FeedProvider feedProvider = new HttpFeedProvider(restTemplate, starlingHost);

        Account account = new Account(
                "ac82f660-5442-4b78-9038-2b72b1206390",
                "badDefaultCategory"
        );
        LocalDate from = LocalDate.of(2020, Month.MARCH, 16);
        LocalDate to = LocalDate.of(2020, Month.MARCH, 23);

        Exception exception = assertThrows(Exception.class, () -> {
            feedProvider.fetch(account, from, to);
        });

        assertThat(exception, instanceOf(FeedNotFoundException.class));
    }
}
