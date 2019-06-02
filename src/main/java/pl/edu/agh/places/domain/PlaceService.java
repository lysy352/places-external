package pl.edu.agh.places.domain;

import org.springframework.stereotype.Component;
import pl.edu.agh.places.domain.dto.Place;
import pl.edu.agh.places.domain.dto.SearchArea;
import pl.edu.agh.places.external.google.GooglePlacesService;
import pl.edu.agh.places.external.yelp.YelpPlacesService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Function;

@Component
public class PlaceService {

    private final GooglePlacesService googlePlacesService;
    private final YelpPlacesService yelpPlacesService;
    private final PlacesMerger placesMerger;

    public PlaceService(GooglePlacesService googlePlacesService, YelpPlacesService yelpPlacesService, PlacesMerger placesMerger) {
        this.googlePlacesService = googlePlacesService;
        this.yelpPlacesService = yelpPlacesService;
        this.placesMerger = placesMerger;
    }

    public Flux<Place> getPlaces(String query, SearchArea searchArea) {
        return Mono.zip(
                googlePlacesService.find(query, searchArea).collectList(),
                yelpPlacesService.find(query, searchArea).collectList(),
                this::merge
        )
                .flatMapIterable(Function.identity());
    }

    private List<Place> merge(List<Place> p1, List<Place> p2) {
        return placesMerger.merge(p1, p2);
    }

}
