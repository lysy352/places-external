package pl.edu.agh.places.external.yelp.api.client;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Component
@Log4j2
@Getter
public class YelpClient {

    private static final String AUTHORIZATION_HEADER_PATTERN = "Bearer %s";
    private final String apiUrl;
    private final String key;
    private final String authorizationHeaderValue;
    private final WebClient webClient;

    public YelpClient(
            WebClient.Builder webClientBuilder,
            @Value("${yelp.api.url}") String apiUrl,
            @Value("${yelp.api.key}") String key

    ) {
        this.apiUrl = apiUrl;
        this.key = key;
        this.authorizationHeaderValue = getAuthorizationHeaderValue(key);
        this.webClient = createWebClient(webClientBuilder);
    }

    private static String getAuthorizationHeaderValue(String key) {
        return String.format(AUTHORIZATION_HEADER_PATTERN, key);
    }

    private WebClient createWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, authorizationHeaderValue)
                .filter(logRequest())
                .filter(logRequesta())
                .clientConnector(new ReactorClientHttpConnector(
                        HttpClient.create().followRedirect(true)
                ))
                .build();
    }

    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
            return Mono.just(clientRequest);
        });
    }

    private ExchangeFilterFunction logRequesta() {
        return ExchangeFilterFunction.ofResponseProcessor(clientRequest -> {
            log.info("Response: {}", clientRequest.rawStatusCode());
            return Mono.just(clientRequest);
        });
    }

}
