package pl.edu.agh.places.external.google.factory;

import org.springframework.stereotype.Component;
import pl.edu.agh.places.domain.PlaceIdFactory;
import pl.edu.agh.places.external.google.api.details.response.AddressComponent;

import java.util.*;

@Component
public class IdFactory {

    private static String STREET_NUMBER = "street_number";
    private static String STREET = "route";
    private static String CITY = "locality";

    private final PlaceIdFactory placeIdFactory;

    public IdFactory(PlaceIdFactory placeIdFactory) {
        this.placeIdFactory = placeIdFactory;
    }

    public String from(List<AddressComponent> addressComponents) {
        Map<String, List<AddressComponent>> components = groupAddressComponents(addressComponents);
        return placeIdFactory.createId(
                this.getDefaultName(STREET, components),
                this.getDefaultName(STREET_NUMBER, components),
                this.getDefaultName(CITY, components)
        );
    }

    private Map<String, List<AddressComponent>> groupAddressComponents(List<AddressComponent> addressComponents) {
        Map<String, List<AddressComponent>> grouped = new HashMap<>();
        addressComponents.forEach(addressComponent ->
                addressComponent.getTypes().forEach(type -> {
                    grouped.putIfAbsent(type, new ArrayList<>());
                    grouped.get(type).add(addressComponent);
                }));
        return grouped;
    }

    private Optional<String> getDefaultName(String componentType, Map<String, List<AddressComponent>> components) {
        return Optional.ofNullable(components.get(componentType))
                .map(this::getDefaultName);
    }

    private String getDefaultName(List<AddressComponent> components) {
        return components.get(0).getLongName();
    }

}
