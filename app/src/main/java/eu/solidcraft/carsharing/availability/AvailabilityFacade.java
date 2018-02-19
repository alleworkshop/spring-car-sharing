package eu.solidcraft.carsharing.availability;

import eu.solidcraft.carsharing.kernel.CarId;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AvailabilityFacade {

    public Mono<Boolean> isAvailable(CarId carId) {
        return null;
    }
}
