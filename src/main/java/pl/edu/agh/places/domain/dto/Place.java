package pl.edu.agh.places.domain.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class Place {

    String id;
    String name;
    String category;
    String address;
    Geo geo;
    String phone;
    String description;
    List<Review> reviews;
    List<Photo> photos;

}
