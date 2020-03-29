package dev.luke10x.starling.roundup;

import dev.luke10x.starling.roundup.domain.RoundupCalculatedEventListener;
import dev.luke10x.starling.roundup.domain.RoundupCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootApplication
public class RoundupApplication {

    @Value("${roundup.access_token}")
    private String roundupAccessToken;

    @Value("${roundup.starling_host}")
    private String starlingHost;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .additionalInterceptors(new HeadersInterceptor(roundupAccessToken))
                .build();
    }

    @Autowired
    RoundupCommand roundupCommand;

    @Profile("!test")
    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            if (args.length < 2) {
                System.err.println("Usage: roundup.jar YEAR WEEK");
                System.exit(1);
            }

            int year = Integer.parseInt(args[0]);
            int week = Integer.parseInt(args[1]);

            LOG.info("ROUNDUP_STARLING_HOST: "+starlingHost);
            LOG.info("ROUNDUP_ACCESS_TOKEN: "+roundupAccessToken);
            LOG.info("Roundup for year: "+year+", week: "+week);

            roundupCommand.execute(year, week);
        };
    }

    @Bean
    RoundupCalculatedEventListener roundupCalculatedEventListener() {
        return roundupCalculatedEvent
                -> LOG.info("\uD83D\uDCA1 ROUNDUP CALCULATED: "+roundupCalculatedEvent.getRoundup());
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

        SpringApplication app = new SpringApplication(RoundupApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }
}