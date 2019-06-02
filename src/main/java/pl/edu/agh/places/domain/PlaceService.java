package pl.edu.agh.places.domain;

import org.springframework.stereotype.Component;
import pl.edu.agh.places.domain.dto.Place;
import pl.edu.agh.places.domain.dto.SearchArea;
import pl.edu.agh.places.external.google.GooglePlacesService;
import pl.edu.agh.places.external.yelp.YelpPlacesService;
import reactor.core.publisher.Flux;

@Component
public class PlaceService {

    private final GooglePlacesService googlePlacesProvider;
    private final YelpPlacesService yelpPlacesService;

    public PlaceService(GooglePlacesService googlePlacesProvider, YelpPlacesService yelpPlacesService) {
        this.googlePlacesProvider = googlePlacesProvider;
        this.yelpPlacesService = yelpPlacesService;
    }

    public Flux<Place> getPlaces(String query, SearchArea searchArea) {
        return yelpPlacesService.find(query, searchArea);
    }

}
