package pl.edu.agh.places.external.google.factory;

import org.springframework.stereotype.Component;
import pl.edu.agh.places.external.google.api.details.response.AddressComponent;

import java.util.*;

@Component
public class IdFactory {

    private static String STREET_NUMBER = "street_number";
    private static String STREET = "route";
    private static String CITY = "locality";
    private static String ID_PATTERN = "%s_%s_%s";

    public String from(List<AddressComponent> addressComponents) {
        Map<String, List<AddressComponent>> components = groupAddressComponents(addressComponents);
        return String.format(ID_PATTERN,
                this.getDefaultName(STREET, components),
                this.getDefaultName(STREET_NUMBER, components),
                this.getDefaultName(CITY, components)
        )
                .toLowerCase();
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

    private String getDefaultName(String componentType, Map<String, List<AddressComponent>> components) {
        return Optional.ofNullable(components.get(componentType))
                .map(this::getDefaultName)
                .orElse("");
    }

    private String getDefaultName(List<AddressComponent> components) {
        return components.get(0).getLongName();
    }

}
