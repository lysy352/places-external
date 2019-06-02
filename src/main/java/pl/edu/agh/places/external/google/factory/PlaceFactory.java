package pl.edu.agh.places.external.google.factory;

import org.springframework.lang.Nullable;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlaceFactory {

    private final IdFactory idFactory;
    private final ReviewFactory reviewFactory;
    private final PhotoFactory photoFactory;

    public PlaceFactory(IdFactory idFactory, ReviewFactory reviewFactory, PhotoFactory photoFactory) {
        this.idFactory = idFactory;
        this.reviewFactory = reviewFactory;
        this.photoFactory = photoFactory;
    }

    public Place create(SearchResult searchResult, DetailsResult detailsResult) {
        return Place.builder()
                .id(idFactory.from(detailsResult.getAddressComponents()))
                .providerId(searchResult.getPlaceId())
                .name(searchResult.getName())
                .category(getCategory(searchResult.getTypes()))
                .address(searchResult.getFormattedAddress())
                .geo(createGeo(searchResult.getGeometry()))
                .phone(detailsResult.getFormattedPhoneNumber())
                .reviews(createReviews(detailsResult.getReviews()))
                .photos(createPhotos(detailsResult.getPhotos()))
                .rating(detailsResult.getRating())
                .description(detailsResult.getUrl())
                .build();
    }

    private String getCategory(List<String> types) {
        return types.isEmpty() ? null : types.get(0);
    }

    private Geo createGeo(SearchGeometry searchGeometry) {
        SearchLocation location = searchGeometry.getLocation();
        return new Geo(location.getLat(), location.getLng());
    }

    private List<Review> createReviews(@Nullable List<DetailsReview> detailsReviews) {
        return Optional.ofNullable(detailsReviews)
                .orElse(Collections.emptyList())
                .stream()
                .map(reviewFactory::create)
                .collect(Collectors.toList());
    }

    private List<Photo> createPhotos(@Nullable List<DetailsPhoto> detailsPhotos) {
        return Optional.ofNullable(detailsPhotos)
                .orElse(Collections.emptyList())
                .stream()
                .map(photoFactory::create)
                .collect(Collectors.toList());
    }

}
