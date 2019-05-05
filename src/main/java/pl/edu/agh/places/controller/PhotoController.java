package pl.edu.agh.places.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.edu.agh.places.external.google.api.photo.GooglePlacesPhotoService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/photos")
public class PhotoController {

    private final GooglePlacesPhotoService googlePhotoService;

    public PhotoController(GooglePlacesPhotoService googlePhotoService) {
        this.googlePhotoService = googlePhotoService;
    }

    @GetMapping(value = "/google/{reference}", produces = MediaType.IMAGE_JPEG_VALUE)
    public Mono<byte[]> getPhoto(
            @PathVariable String reference,
            @RequestParam(defaultValue = "800") int maxWidth,
            @RequestParam(defaultValue = "800") int maxHeight
    ) {
        return googlePhotoService.fetchPhoto(reference, maxWidth, maxHeight);
    }
}
