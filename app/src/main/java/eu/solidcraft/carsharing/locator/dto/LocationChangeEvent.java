package eu.solidcraft.carsharing.locator.dto;

import eu.solidcraft.carsharing.kernel.GpsId;
import eu.solidcraft.carsharing.kernel.Location;
import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LocationChangeEvent {
    Location location;
    GpsId gpsId;
}
