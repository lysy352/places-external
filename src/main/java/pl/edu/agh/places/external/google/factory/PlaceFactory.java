package pl.edu.agh.places.external.google.factory;

import org.springframework.stereotype.Component;
import pl.edu.agh.places.domain.dto.Geo;
import pl.edu.agh.places.domain.dto.Photo;
import pl.edu.agh.places.domain.dto.Place;
import pl.edu.agh.places.domain.dto.Review;
import pl.edu.agh.places.external.google.api.details.response.DetailsPhoto;
import pl.edu.agh.places.external.google.api.details.response.DetailsResult;
import pl.edu.agh.places.external.google.api.details.response.DetailsReview;
import pl.edu.agh.places.external.google.api.search.response.SearchGeometry;
import pl.edu.agh.places.external.google.api.search.response.SearchLocation;
import pl.edu.agh.places.external.google.api.search.response.SearchResult;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlaceFactory {

    private final ReviewFactory reviewFactory;
    private final PhotoFactory photoFactory;

    public PlaceFactory(ReviewFactory reviewFactory, PhotoFactory photoFactory) {
        this.reviewFactory = reviewFactory;
        this.photoFactory = photoFactory;
    }

    public Place create(SearchResult searchResult, DetailsResult detailsResult) {
        return Place.builder()
                .id(searchResult.getPlaceId())
                .name(searchResult.getName())
                .category(getCategory(searchResult.getTypes()))
                .address(searchResult.getFormattedAddress())
                .geo(createGeo(searchResult.getGeometry()))
                .phone(detailsResult.getFormattedPhoneNumber())
                .reviews(createReviews(detailsResult.getReviews()))
                .photos(createPhotos(detailsResult.getPhotos()))
                .build();
    }

    private String getCategory(List<String> types) {
        return types.isEmpty() ? null : types.get(0);
    }

    private Geo createGeo(SearchGeometry searchGeometry) {
        SearchLocation location = searchGeometry.getLocation();
        return new Geo(location.getLat(), location.getLng());
    }

    private List<Review> createReviews(List<DetailsReview> detailsReviews) {
        return detailsReviews.stream()
                .map(reviewFactory::create)
                .collect(Collectors.toList());
    }

    private List<Photo> createPhotos(List<DetailsPhoto> detailsPhotos) {
        return detailsPhotos.stream()
                .map(photoFactory::create)
                .collect(Collectors.toList());
    }

}
