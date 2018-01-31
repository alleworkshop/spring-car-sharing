package eu.solidcraft.carsharing.search.dto;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarDto {

    String name;

    public CarDto(String name) {
        this.name = name;
    }
}
