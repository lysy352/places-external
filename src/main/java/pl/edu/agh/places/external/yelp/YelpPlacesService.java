package pl.edu.agh.places.external.yelp;

import org.springframework.stereotype.Component;
import pl.edu.agh.places.domain.dto.Place;
import pl.edu.agh.places.domain.dto.SearchArea;
import pl.edu.agh.places.external.PlacesExternalService;
import pl.edu.agh.places.external.yelp.api.details.YelpDetailsService;
import pl.edu.agh.places.external.yelp.api.reviews.YelpReviewsService;
import pl.edu.agh.places.external.yelp.api.search.YelpSearchService;
import pl.edu.agh.places.external.yelp.factory.YelpPlaceFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class YelpPlacesService implements PlacesExternalService {

    private final YelpSearchService searchService;
    private final YelpDetailsService detailsService;
    private final YelpReviewsService reviewsService;
    private final YelpPlaceFactory placeFactory;

    public YelpPlacesService(
            YelpSearchService searchService,
            YelpDetailsService detailsService,
            YelpReviewsService reviewsService,
            YelpPlaceFactory placeFactory
    ) {
        this.searchService = searchService;
        this.detailsService = detailsService;
        this.reviewsService = reviewsService;
        this.placeFactory = placeFactory;
    }

    @Override
    public Flux<Place> find(String query, SearchArea searchArea) {
        return searchService.fetchPlaces(query, searchArea)
                .take(5)
                .flatMap(place -> {
                    var id = place.getId();
                    return Mono.zip(
                            detailsService.fetchDetails(id),
                            reviewsService.fetchReviews(id).collectList(),
                            (details, reviews) -> placeFactory.from(place, details, reviews)
                    );
                });
    }

}
