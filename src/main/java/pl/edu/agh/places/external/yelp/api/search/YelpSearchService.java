package pl.edu.agh.places.external.yelp.api.search;

import org.springframework.stereotype.Component;
import pl.edu.agh.places.domain.dto.SearchArea;
import pl.edu.agh.places.external.yelp.api.client.YelpClient;
import pl.edu.agh.places.external.yelp.api.search.response.YelpPlace;
import pl.edu.agh.places.external.yelp.api.search.response.YelpSearch;
import reactor.core.publisher.Flux;

@Component
public class YelpSearchService {

    private static final String URL = "/businesses/search";
    private final YelpClient client;

    public YelpSearchService(YelpClient client) {
        this.client = client;
    }

    public Flux<YelpPlace> fetchPlaces(String query, SearchArea searchArea) {
        var geo = searchArea.getGeo();
        return client.getWebClient()
                .get()
                .uri(builder -> builder
                        .path(URL)
                        .queryParam("term", query)
                        .queryParam("latitude", geo.getLat())
                        .queryParam("longitude", geo.getLng())
                        .build()
                )
                .retrieve()
                .bodyToMono(YelpSearch.class)
                .flatMapIterable(YelpSearch::getBusinesses);
    }

}
