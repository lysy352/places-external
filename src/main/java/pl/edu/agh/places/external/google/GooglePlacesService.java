package pl.edu.agh.places.external.google;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import pl.edu.agh.places.domain.dto.Place;
import pl.edu.agh.places.domain.dto.SearchArea;
import pl.edu.agh.places.external.google.api.details.PlaceDetailsService;
import pl.edu.agh.places.external.google.api.details.response.DetailsResponse;
import pl.edu.agh.places.external.google.api.details.response.DetailsResult;
import pl.edu.agh.places.external.google.api.search.PlaceSearchService;
import pl.edu.agh.places.external.google.api.search.response.SearchResponse;
import pl.edu.agh.places.external.google.api.search.response.SearchResult;
import pl.edu.agh.places.external.google.factory.PlaceFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
@Log4j2
public class GooglePlacesService {

    private final PlaceSearchService placeSearchService;
    private final PlaceDetailsService placeDetailsService;
    private final PlaceFactory placeFactory;

    public GooglePlacesService(
            PlaceSearchService placeSearchService,
            PlaceDetailsService placeDetailsService,
            PlaceFactory placeFactory
    ) {
        this.placeSearchService = placeSearchService;
        this.placeDetailsService = placeDetailsService;
        this.placeFactory = placeFactory;
    }

    public Flux<Place> find(String query, SearchArea searchArea) {
        return placeSearchService.find(query, searchArea)
                .flatMapIterable(SearchResponse::getResults)
                .flatMap(this::updateWithDetails);
    }

    private Mono<Place> updateWithDetails(SearchResult searchResult) {
        return fetchDetails(searchResult.getPlaceId())
                .map(details -> placeFactory.create(searchResult, details));
    }

    private Mono<DetailsResult> fetchDetails(String placeId) {
        return placeDetailsService.fetchDetails(placeId)
                .subscribeOn(Schedulers.elastic())
                .map(DetailsResponse::getResult);
    }

}
