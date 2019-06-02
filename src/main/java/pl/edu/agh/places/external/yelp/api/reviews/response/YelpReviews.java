package pl.edu.agh.places.external.yelp.api.reviews.response;

import lombok.Value;

import java.util.List;

@Value
public class YelpReviews {

    List<YelpReview> reviews;
    int total;

}
