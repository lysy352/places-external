package pl.edu.agh.places.external.google.api.photo;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import pl.edu.agh.places.external.google.api.client.GooglePlacesClient;
import reactor.core.publisher.Mono;

@Component
@Log4j2
public class GooglePlacesPhotoService {

    private final GooglePlacesClient client;

    public GooglePlacesPhotoService(GooglePlacesClient client) {
        this.client = client;
    }

    public Mono<byte[]> fetchPhoto(String reference, int maxWidth, int maxHeight) {
        return client.getWebClient()
                .get()
                .uri(builder -> builder
                        .path("/photo")
                        .queryParam("key", client.getKey())
                        .queryParam("photoreference", reference)
                        .queryParam("maxwidth", maxWidth)
                        .queryParam("maxheight", maxHeight)
                        .build()
                )
                .retrieve()
                .bodyToMono(byte[].class);
    }

}
