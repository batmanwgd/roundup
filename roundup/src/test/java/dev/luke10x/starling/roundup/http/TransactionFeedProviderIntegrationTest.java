package dev.luke10x.starling.roundup.http;

import dev.luke10x.starling.roundup.RoundupApplication;
import dev.luke10x.starling.roundup.domain.TransactionFeedProvider;
import dev.luke10x.starling.roundup.domain.accounts.Account;
import dev.luke10x.starling.roundup.domain.feed.FeedItem;
import dev.luke10x.starling.roundup.domain.feed.TransactionFeed;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = { RoundupApplication.class })
@EnableConfigurationProperties
public class TransactionFeedProviderIntegrationTest {

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
    void fetchesFeedFromHttp() throws IOException {

        String starlingHost = "http://localhost:" + starlingAPI.getPort();

        TransactionFeedProvider transactionFeedProvider = new HttpTransactionFeedProvider(restTemplate, starlingHost);

        Account account = new Account(
                "ac82f660-5442-4b78-9038-2b72b1206390",
                "2eb42e49-f275-4019-8707-81a0637e7206"
        );
        LocalDate from = LocalDate.of(2020, Month.MARCH, 20);

        TransactionFeed feed = transactionFeedProvider.fetch(account, from);

        assertNotNull(feed);
        assertTrue(feed.getFeedItems().size() > 0);

        FeedItem first = feed.getFeedItems().get(0);
        assertEquals("OUT", first.getDirection());
        assertEquals(3909, first.getAmount().getMinorUnits());
        assertEquals("GBP", first.getAmount().getCurrency());
    }
}
