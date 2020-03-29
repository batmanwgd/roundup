package dev.luke10x.starling.roundup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootApplication
public class RoundupApplication implements CommandLineRunner {

    @Value("${roundup.access_token}")
    private String roundupAccessToken;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .additionalInterceptors(new HeadersInterceptor(roundupAccessToken))
                .build();
    }

    class HeadersInterceptor implements ClientHttpRequestInterceptor {

        private String roundupAccessToken;

        public HeadersInterceptor(String roundupAccessToken) {

            this.roundupAccessToken = roundupAccessToken;
        }

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                            ClientHttpRequestExecution execution) throws IOException {
            HttpHeaders headers = request.getHeaders();
            headers.add("Authorization", "Bearer " + roundupAccessToken);
            return execution.execute(request, body);
        }
    }

    private static Logger LOG = LoggerFactory
            .getLogger(RoundupApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(RoundupApplication.class, args);
    }

    @Override
    public void run(String... args) {
        LOG.info("EXECUTING : command line runner");
    }
}