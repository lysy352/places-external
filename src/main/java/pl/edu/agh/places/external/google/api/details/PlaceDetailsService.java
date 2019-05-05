package pl.edu.agh.places.external.google.api.details;

import org.springframework.stereotype.Component;
import pl.edu.agh.places.external.google.api.client.GooglePlacesClient;
import pl.edu.agh.places.external.google.api.details.response.DetailsResponse;
import reactor.core.publisher.Mono;

@Component
public class PlaceDetailsService {

    private static final String FIELDS = String.join(",",
            "review",
            "formatted_phone_number",
            "photo"
    );

    private final GooglePlacesClient client;

    public PlaceDetailsService(
            GooglePlacesClient client
    ) {
        this.client = client;
    }

    public Mono<DetailsResponse> fetchDetails(String placeId) {
        return client.getWebClient()
                .get()
                .uri(builder -> builder
                        .path("/details/json")
                        .queryParam("key", client.getKey())
                        .queryParam("language", client.getLanguage())
                        .queryParam("placeid", placeId)
                        .queryParam("fields", FIELDS)
                        .build()
                )
                .retrieve()
                .bodyToMono(DetailsResponse.class);
    }

}
