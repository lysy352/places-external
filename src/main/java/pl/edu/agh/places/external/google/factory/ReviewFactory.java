package pl.edu.agh.places.external.google.factory;

import org.springframework.stereotype.Component;
import pl.edu.agh.places.domain.dto.Provider;
import pl.edu.agh.places.domain.dto.Review;
import pl.edu.agh.places.external.google.api.details.response.DetailsReview;

import java.time.Instant;

@Component
public class ReviewFactory {

    public Review create(DetailsReview detailsReview) {
        return Review.builder()
                .date(Instant.ofEpochMilli(detailsReview.getTime()))
                .author(detailsReview.getAuthorName())
                .rating(detailsReview.getRating())
                .text(detailsReview.getText())
                .provider(Provider.GOOGLE)
                .url(detailsReview.getAuthorUrl())
                .build();
    }

}
