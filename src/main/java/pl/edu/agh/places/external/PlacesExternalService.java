package pl.edu.agh.places.external;

import pl.edu.agh.places.domain.dto.Place;
import pl.edu.agh.places.domain.dto.SearchArea;
import reactor.core.publisher.Flux;

public interface PlacesExternalService {

    Flux<Place> find(String query, SearchArea searchArea);

}