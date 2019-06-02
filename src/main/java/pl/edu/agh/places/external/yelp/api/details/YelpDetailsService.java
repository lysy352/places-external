package pl.edu.agh.places.external.yelp.api.details;

import org.springframework.stereotype.Component;
import pl.edu.agh.places.external.yelp.api.client.YelpClient;
import pl.edu.agh.places.external.yelp.api.details.response.YelpDetails;
import reactor.core.publisher.Mono;

@Component
public class YelpDetailsService {

    private static final String URL = "/businesses/%s";
    private final YelpClient client;

    public YelpDetailsService(YelpClient client) {
        this.client = client;
    }

    public Mono<YelpDetails> fetchDetails(String placeId) {
        return client.getWebClient()
                .get()
                .uri(getUrl(placeId))
                .retrieve()
                .bodyToMono(YelpDetails.class);
    }

    private static String getUrl(String placeId) {
        return String.format(URL, placeId);
    }

}
