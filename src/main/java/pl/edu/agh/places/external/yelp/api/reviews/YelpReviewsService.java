package pl.edu.agh.places.external.yelp.api.reviews;

import org.springframework.stereotype.Component;
import pl.edu.agh.places.external.yelp.api.client.YelpClient;
import pl.edu.agh.places.external.yelp.api.reviews.response.YelpReview;
import pl.edu.agh.places.external.yelp.api.reviews.response.YelpReviews;
import reactor.core.publisher.Flux;

@Component
public class YelpReviewsService {

    private static final String URL = "/businesses/%s/reviews";
    private final YelpClient client;

    public YelpReviewsService(YelpClient client) {
        this.client = client;
    }

    public Flux<YelpReview> fetchReviews(String placeId) {
        return client.getWebClient()
                .get()
                .uri(getUrl(placeId))
                .retrieve()
                .bodyToMono(YelpReviews.class)
                .flatMapIterable(YelpReviews::getReviews);
    }

    private static String getUrl(String placeId) {
        return String.format(URL, placeId);
    }

}
