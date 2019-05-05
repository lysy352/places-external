package pl.edu.agh.places.external.google.api.details.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

@Value
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DetailsReview {

    String authorName;
    String authorUrl;
    String profilePhotoUrl;
    String language;
    int rating;
    String relativeTimeDescription;
    String text;
    long time;

}
