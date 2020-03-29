package dev.luke10x.starling.roundup;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
@ActiveProfiles("test")
class RoundupApplicationTests {

	@Test
	void contextLoads() {
	}
}
