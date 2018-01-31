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

    public LocationDto shift(BigDecimal deltaLat, BigDecimal deltaLon) {
        return new LocationDto(lat.add(deltaLat), lon.add(deltaLon));
    }

    public BigDecimal distanceTo(LocationDto location) {
        return BigDecimal.valueOf(
                Math.sqrt(
                        lat.subtract(location.lat).pow(2)
                                .add(lon.subtract(location.lon).pow(2))
                                .doubleValue()));
    }
}
