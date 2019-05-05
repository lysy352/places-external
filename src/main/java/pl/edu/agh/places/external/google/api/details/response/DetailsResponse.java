package pl.edu.agh.places.external.google.api.details.response;

import lombok.Value;

@Value
public class DetailsResponse {

    DetailsResult result;
    String status;

}
