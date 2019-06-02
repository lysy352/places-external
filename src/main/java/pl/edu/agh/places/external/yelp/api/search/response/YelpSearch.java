package pl.edu.agh.places.external.yelp.api.search.response;

import lombok.Value;

import java.util.List;

@Value
public class YelpSearch {

    int total;
    List<YelpPlace> businesses;

}
