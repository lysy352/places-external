package pl.edu.agh.places.external.google.api.search.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;
import pl.edu.agh.places.external.google.api.details.response.DetailsPhoto;

import java.util.List;

@Value
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SearchResult {

    String id;
    String placeId;
    SearchGeometry geometry;
    String name;
    String formattedAddress;
    double rating;
    int userRatingsTotal;
    List<String> types;
    String reference;
    List<DetailsPhoto> photos;
    String icon;

}
