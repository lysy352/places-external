package pl.edu.agh.places.domain.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class Place {

    String id;
    String providerId;
    String name;
    String category;
    String address;
    Geo geo;
    String phone;
    String description;
    List<Review> reviews;
    List<Photo> photos;
    int rating;

}
