package eu.solidcraft.carsharing.search;

import eu.solidcraft.carsharing.search.dto.CarDto;
import eu.solidcraft.carsharing.search.dto.LocationDto;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Flux;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchFacade {
    public Flux<CarDto> findCarsNearby(LocationDto location) {
        return null;
    }
}
