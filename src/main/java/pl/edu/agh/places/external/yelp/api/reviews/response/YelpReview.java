package pl.edu.agh.places.external.yelp.api.reviews.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

@Value
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class YelpReview {

    String url;
    String text;
    User user;
    int rating;
    String timeCreated;

    @Value
    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class User {

        String imageUrl;
        String name;

    }

}
