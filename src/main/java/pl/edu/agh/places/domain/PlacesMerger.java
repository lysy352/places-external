package pl.edu.agh.places.domain;

import org.springframework.stereotype.Component;
import pl.edu.agh.places.domain.dto.Photo;
import pl.edu.agh.places.domain.dto.Place;
import pl.edu.agh.places.domain.dto.Review;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class PlacesMerger {

    public List<Place> merge(List<Place>... places) {
        Stream.Builder<Place> streamBuilder = Stream.builder();
        Arrays.stream(places).forEach(placesList -> placesList.forEach(streamBuilder::add));

        return streamBuilder.build()
                .collect(Collectors.groupingBy(Place::getId))
                .values()
                .stream()
                .map(this::merge)
                .collect(Collectors.toList());
    }

    private Place merge(List<Place> places) {
        List<Review> reviews = new LinkedList<>();
        List<Photo> photos = new LinkedList<>();
        Rating rating = new Rating();

        places.forEach(place -> {
            reviews.addAll(place.getReviews());
            photos.addAll(place.getPhotos());
            rating.addRating(place.getRating());
        });

        return places.get(0).toBuilder()
                .reviews(reviews)
                .photos(photos)
                .rating(rating.getRating())
                .build();
    }

    private static class Rating {
        int sum = 0;
        int elements = 0;

        void addRating(int rating) {
            sum += rating;
            elements += 1;
        }

        int getRating() {
            return Math.round((float) sum / elements);
        }
    }

}
