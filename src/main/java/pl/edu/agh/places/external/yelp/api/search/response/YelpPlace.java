package pl.edu.agh.places.external.yelp.api.search.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;
import pl.edu.agh.places.external.yelp.api.details.response.YelpCoordinates;
import pl.edu.agh.places.external.yelp.api.details.response.YelpLocation;

import java.util.List;

@Value
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class YelpPlace {

    String id;
    String alias;
    String name;
    String imageUrl;
    String url;
    int reviewCount;
    List<YelpCategory> categories;
    int rating;
    YelpCoordinates coordinates;
    YelpLocation location;
    String phone;
    String displayPhone;

}
