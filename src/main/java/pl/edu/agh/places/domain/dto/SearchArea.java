package pl.edu.agh.places.domain.dto;

import lombok.Value;

@Value
public class SearchArea {

    Geo geo;
    int radius;

}
