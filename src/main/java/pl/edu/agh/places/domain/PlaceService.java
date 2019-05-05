package pl.edu.agh.places.domain;

import org.springframework.stereotype.Component;
import pl.edu.agh.places.domain.dto.Place;
import pl.edu.agh.places.domain.dto.SearchArea;
import pl.edu.agh.places.external.google.GooglePlacesService;
import reactor.core.publisher.Flux;

@Component
public class PlaceService {

    private final GooglePlacesService googlePlacesProvider;

    public PlaceService(GooglePlacesService googlePlacesProvider) {
        this.googlePlacesProvider = googlePlacesProvider;
    }

    public Flux<Place> getPlaces(String query, SearchArea searchArea) {
        return googlePlacesProvider.find(query, searchArea);
    }

}
