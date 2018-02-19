package eu.solidcraft.carsharing.car.dto;

import eu.solidcraft.carsharing.kernel.CarId;
import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;

@Value
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CarDto {
    CarId carId;
}
