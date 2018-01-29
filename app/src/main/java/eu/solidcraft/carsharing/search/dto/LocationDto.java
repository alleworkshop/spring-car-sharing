package eu.solidcraft.carsharing.search.dto;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Value
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationDto {
    BigDecimal lat;
    BigDecimal lon;
}
