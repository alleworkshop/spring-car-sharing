package eu.solidcraft.carsharing.search.dto;

import eu.solidcraft.carsharing.kernel.CarId;
import eu.solidcraft.carsharing.kernel.Location;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarInLocationDto {
    @NonNull CarId carId;
    @NonNull Location location;
}
