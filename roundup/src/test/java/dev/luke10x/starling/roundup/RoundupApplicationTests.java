package dev.luke10x.starling.roundup;

import dev.luke10x.starling.roundup.domain.RoundupCollector;
import dev.luke10x.starling.roundup.domain.DateResolver;
import dev.luke10x.starling.roundup.domain.RoundupCommand;
import dev.luke10x.starling.roundup.domain.FeedCalculator;
import dev.luke10x.starling.roundup.http.HttpAccountsProvider;
import dev.luke10x.starling.roundup.http.HttpFeedProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations="classpath:test.properties")
@ActiveProfiles("test")
@SpringBootTest(classes = { RoundupApplication.class })
class RoundupApplicationTests {

    @Autowired
    FeedCalculator feedCalculator;

    @Autowired
    HttpAccountsProvider accountsProvider;

    @Autowired
    HttpFeedProvider transactionFeedProvider;

    @Autowired
    DateResolver dateResolver;

    @Autowired
    RoundupCollector roundupCollector;

    @Autowired
    RoundupCommand roundupCommand;

    @Test
	void contextLoads() {
    }
}
