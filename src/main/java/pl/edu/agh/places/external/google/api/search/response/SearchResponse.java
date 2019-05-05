package pl.edu.agh.places.external.google.api.search.response;

import lombok.Value;

import java.util.List;

@Value
public class SearchResponse {

    List<SearchResult> results;
    String status;

}
