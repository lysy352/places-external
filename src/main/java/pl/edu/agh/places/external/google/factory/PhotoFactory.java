package pl.edu.agh.places.external.google.factory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pl.edu.agh.places.domain.dto.Photo;
import pl.edu.agh.places.external.google.api.details.response.DetailsPhoto;

@Component
public class PhotoFactory {

    private static final String GOOGLE_PHOTO_PATH = "/v1/photos/google/";

    private final String domain;

    public PhotoFactory(@Value("${app.domain}") String domain) {
        this.domain = domain;
    }

    public Photo create(DetailsPhoto detailsPhoto) {
        return new Photo(getUrl(detailsPhoto.getPhotoReference()));
    }

    private String getUrl(String photoReference) {
        return String.format("%s%s%s", domain, GOOGLE_PHOTO_PATH, photoReference);
    }

}
