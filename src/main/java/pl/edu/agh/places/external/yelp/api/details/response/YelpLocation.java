package pl.edu.agh.places.external.yelp.api.details.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Value;

import java.util.List;

@Value
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class YelpLocation {

    String city;
    String country;
    String address2;
    String address3;
    String address1;
    String zipCode;
    String state;
    List<String> displayAddress;

}
