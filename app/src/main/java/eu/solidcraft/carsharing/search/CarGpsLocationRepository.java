package eu.solidcraft.carsharing.search;

import eu.solidcraft.carsharing.search.dto.LocationDto;
import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

interface CarGpsLocationRepository {
    Mono<Void> upsert(String gpsId, LocationDto location);

    Flux<CarGpsLocation> findNearestFrom(LocationDto location);

    @Value
    class CarGpsLocation {
        String gpsId;
        LocationDto location;
    }
}
