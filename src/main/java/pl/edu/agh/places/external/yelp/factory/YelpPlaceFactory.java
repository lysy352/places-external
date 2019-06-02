package pl.edu.agh.places.external.yelp.factory;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.edu.agh.places.domain.dto.Geo;
import pl.edu.agh.places.domain.dto.Photo;
import pl.edu.agh.places.domain.dto.Place;
import pl.edu.agh.places.domain.dto.Review;
import pl.edu.agh.places.external.yelp.api.details.response.YelpCoordinates;
import pl.edu.agh.places.external.yelp.api.details.response.YelpDetails;
import pl.edu.agh.places.external.yelp.api.details.response.YelpLocation;
import pl.edu.agh.places.external.yelp.api.reviews.response.YelpReview;
import pl.edu.agh.places.external.yelp.api.search.response.YelpCategory;
import pl.edu.agh.places.external.yelp.api.search.response.YelpPlace;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class YelpPlaceFactory {

    private final YelpIdFactory idFactory;
    private final YelpReviewFactory reviewFactory;

    public YelpPlaceFactory(YelpIdFactory idFactory, YelpReviewFactory reviewFactory) {
        this.idFactory = idFactory;
        this.reviewFactory = reviewFactory;
    }

    public Place from(YelpPlace place, YelpDetails details, List<YelpReview> reviews) {
        return Place.builder()
                .id(idFactory.from(place.getLocation()))
                .description(place.getUrl())
                .providerId(place.getId())
                .name(place.getName())
                .category(getCategory(place.getCategories()))
                .address(createFormattedAddress(place.getLocation()))
                .geo(createGeo(place.getCoordinates()))
                .phone(place.getDisplayPhone())
                .reviews(createReviews(reviews))
                .photos(createPhotos(details.getPhotos()))
                .rating(place.getRating())
                .build();
    }

    @Nullable
    private String getCategory(List<YelpCategory> categories) {
        return Optional.ofNullable(categories.get(0))
                .map(YelpCategory::getTitle)
                .orElse(null);
    }

    private String createFormattedAddress(YelpLocation location) {
        return String.join(", ", location.getDisplayAddress());
    }

    private Geo createGeo(YelpCoordinates coordinates) {
        return new Geo(coordinates.getLatitude(), coordinates.getLongitude());
    }

    private List<Review> createReviews(List<YelpReview> reviews) {
        return reviews.stream()
                .map(reviewFactory::create)
                .collect(Collectors.toList());
    }

    private List<Photo> createPhotos(List<String> photosUrls) {
        return photosUrls.stream()
                .map(Photo::new)
                .collect(Collectors.toList());
    }

}
