package pl.edu.agh.places.external.google.api.details.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

import java.util.List;

@Value
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DetailsPhoto {

    int height;
    int width;
    List<String> htmlAttributions;
    String photoReference;

}
