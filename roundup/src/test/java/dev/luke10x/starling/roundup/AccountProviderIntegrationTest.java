package dev.luke10x.starling.roundup;

import dev.luke10x.starling.roundup.accounts.AccountsResponse;
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
    @Disabled
    void fetchesAccountsResponseFromHttpApi() {

        String starlingHost = "http://localhost:" + starlingAPI.getPort();
        AccountsProvider accountsProvider = new AccountsProvider(restTemplate, starlingHost);

        AccountsResponse response = accountsProvider.fetch();

        assertNotNull(response);
    }
}
