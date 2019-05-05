package pl.edu.agh.places.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.agh.places.domain.PlaceService;
import pl.edu.agh.places.domain.dto.Geo;
import pl.edu.agh.places.domain.dto.Place;
import pl.edu.agh.places.domain.dto.SearchArea;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/places")
public class PlaceController {

    private final PlaceService placeService;

    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping
    public Flux<Place> getPlace(
            @RequestParam String query,
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "1000") int radius
    ) {
        return placeService.getPlaces(
                query, new SearchArea(new Geo(lat, lng), radius)
        );
    }

}
