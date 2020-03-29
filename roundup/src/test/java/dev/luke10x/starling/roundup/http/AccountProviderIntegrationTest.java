package dev.luke10x.starling.roundup.http;

import dev.luke10x.starling.roundup.domain.AccountsProvider;
import dev.luke10x.starling.roundup.RoundupApplication;
import dev.luke10x.starling.roundup.domain.accounts.Account;
import dev.luke10x.starling.roundup.domain.accounts.AccountsResponse;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith({MockitoExtension.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = { RoundupApplication.class })
@EnableConfigurationProperties
public class AccountProviderIntegrationTest {

    static private ClientAndServer starlingAPI;

    @BeforeAll
    public void startStub() throws IOException {
        starlingAPI = ClientAndServer.startClientAndServer(0);

        HttpRequest request = HttpRequest.request()
                .withMethod("GET")
                .withPath("/api/v2/accounts");

        File file = new File(getClass().getClassLoader().getResource("./accounts-response.json").getPath());
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
    void fetchesAccountsResponseFromHttpApi() {

        String starlingHost = "http://localhost:" + starlingAPI.getPort();
        AccountsProvider accountsProvider = new HttpAccountsProvider(restTemplate, starlingHost);

        AccountsResponse response = accountsProvider.fetch();

        assertNotNull(response);
    }

    @Test
    void firstAccountHasAccountUidAndCategoryUid() {

        String starlingHost = "http://localhost:" + starlingAPI.getPort();
        AccountsProvider accountsProvider = new HttpAccountsProvider(restTemplate, starlingHost);

        AccountsResponse response = accountsProvider.fetch();

        Account first = response.getAccounts().get(0);

        assertEquals("TEST0000-0000-0000-0000-ACCOUNT_UID0", first.getAccountUid());
        assertEquals("TEST0000-0000-0000-0000-CATEGORY0000", first.getDefaultCategory());
    }
}
