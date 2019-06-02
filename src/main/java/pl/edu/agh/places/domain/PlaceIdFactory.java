package pl.edu.agh.places.domain;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlaceIdFactory {

    private static String ID_PATTERN = "%s_%s_%s";

    public String createId(@Nullable String streetNumber, @Nullable String street, @Nullable String city) {
        return String.format(ID_PATTERN,
                getOrDefault(street),
                getOrDefault(streetNumber),
                getOrDefault(city)
        )
                .toLowerCase();
    }

    public String createId(Optional<String> streetNumber, Optional<String> street, Optional<String> city) {
        return createId(
                getOrDefault(street),
                getOrDefault(streetNumber),
                getOrDefault(city)
        );
    }

    private String getOrDefault(@Nullable String text) {
        return Optional.ofNullable(text).orElse("");
    }

    private String getOrDefault(Optional<String> text) {
        return text.orElse("");
    }

}
