package pl.edu.agh.places.domain.dto;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class Review {

    Instant date;
    String author;
    Integer rating;
    String text;
    Provider provider;
    String url;

}
