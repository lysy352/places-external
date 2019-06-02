package pl.edu.agh.places.external.yelp.api.details.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

import java.util.List;

@Value
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class YelpDetails {

    String id;
    int rating;
    int reviewsCount;
    String name;
    String url;
    YelpCoordinates coordinates;
    String imageUrl;
    YelpLocation location;
    String price;
    List<String> photos;


}
