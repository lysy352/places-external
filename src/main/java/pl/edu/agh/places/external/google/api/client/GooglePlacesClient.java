package pl.edu.agh.places.external.google.api.client;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Component
@Log4j2
@Getter
public class GooglePlacesClient {

    private final String apiUrl;
    private final String key;
    private final String language;
    private final WebClient webClient;

    public GooglePlacesClient(
            WebClient.Builder webClientBuilder,
            @Value("${google.places.api.url}") String apiUrl,
            @Value("${google.places.api.key}") String key,
            @Value("${google.places.api.language}") String language

    ) {
        this.apiUrl = apiUrl;
        this.key = key;
        this.language = language;
        this.webClient = createWebClient(webClientBuilder);
    }

    @Bean
    private WebClient createWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl(apiUrl)
                .filter(logRequest())
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

}
