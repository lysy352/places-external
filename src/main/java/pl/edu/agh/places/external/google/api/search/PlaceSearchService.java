package pl.edu.agh.places.external.google.api.search;

import org.springframework.stereotype.Component;
import pl.edu.agh.places.domain.dto.Geo;
import pl.edu.agh.places.domain.dto.SearchArea;
import pl.edu.agh.places.external.google.api.client.GooglePlacesClient;
import pl.edu.agh.places.external.google.api.search.response.SearchResponse;
import reactor.core.publisher.Mono;

@Component
public class PlaceSearchService {

    private final GooglePlacesClient client;

    public PlaceSearchService(GooglePlacesClient client) {
        this.client = client;
    }

    public Mono<SearchResponse> find(String query, SearchArea searchArea) {
        return client.getWebClient()
                .get()
                .uri(builder -> builder
                        .path("/textsearch/json")
                        .queryParam("key", client.getKey())
                        .queryParam("language", client.getLanguage())
                        .queryParam("query", query)
                        .queryParam("location", getLocationParam(searchArea.getGeo()))
                        .queryParam("radius", searchArea.getRadius())
                        .build()
                )
                .retrieve()
                .bodyToMono(SearchResponse.class);
    }

    private String getLocationParam(Geo geo) {
        return String.format("%f,%f", geo.getLat(), geo.getLng());
    }

}
