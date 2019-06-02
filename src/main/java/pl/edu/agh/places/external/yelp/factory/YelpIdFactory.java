package pl.edu.agh.places.external.yelp.factory;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import pl.edu.agh.places.domain.PlaceIdFactory;
import pl.edu.agh.places.external.yelp.api.details.response.YelpLocation;

import java.util.Arrays;

@Component
public class YelpIdFactory {

    private final PlaceIdFactory placeIdFactory;

    public YelpIdFactory(PlaceIdFactory placeIdFactory) {
        this.placeIdFactory = placeIdFactory;
    }

    public String from(YelpLocation location) {
        String[] streetStreetAddress = splitStreetAndNumber(location.getAddress1());
        return placeIdFactory.createId(
                replacePrefixes(streetStreetAddress[1]),
                replacePrefixes(streetStreetAddress[0]),
                location.getCity()
        );
    }

    private String[] splitStreetAndNumber(String address) {
        String[] split = address.split("\\s");

        String streetNumber = null;
        String street = address;
        for (int i = split.length - 1; i >= 0; i--) {
            if (StringUtils.isNumeric(split[i])) {
                streetNumber = split[i];
                street = String.join(" ", Arrays.copyOf(split, i));
                break;
            }
        }
        return new String[]{street, streetNumber};
    }

    private String replacePrefixes(String street) {
        return street.replace("ul. ", "");
    }

}
