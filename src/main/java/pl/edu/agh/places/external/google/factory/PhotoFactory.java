package pl.edu.agh.places.external.google.factory;

import org.springframework.stereotype.Component;
import pl.edu.agh.places.domain.dto.Photo;
import pl.edu.agh.places.external.google.api.details.response.DetailsPhoto;

@Component
public class PhotoFactory {

    private static final String GOOGLE_PHOTO_PATH = "/v1/photos/google/%s";

    public Photo create(DetailsPhoto detailsPhoto) {
        return new Photo(getUrl(detailsPhoto.getPhotoReference()));
    }

    private String getUrl(String photoReference) {
        return String.format(GOOGLE_PHOTO_PATH, photoReference);
    }

}
