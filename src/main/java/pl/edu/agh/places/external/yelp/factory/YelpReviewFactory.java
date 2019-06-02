package pl.edu.agh.places.external.yelp.factory;

import org.springframework.stereotype.Component;
import pl.edu.agh.places.domain.dto.Provider;
import pl.edu.agh.places.domain.dto.Review;
import pl.edu.agh.places.external.yelp.api.reviews.response.YelpReview;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Component
public class YelpReviewFactory {

    public Review create(YelpReview review) {
        return Review.builder()
                .date(getDate(review.getTimeCreated()))
                .author(review.getUser().getName())
                .rating(review.getRating())
                .text(review.getText())
                .provider(Provider.GOOGLE)
                .url(review.getUrl())
                .build();
    }

    private Instant getDate(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                .toInstant(ZoneOffset.UTC);
    }

}
