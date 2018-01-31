package eu.solidcraft.carsharing.search;

import eu.solidcraft.carsharing.search.dto.CarDto;
import eu.solidcraft.carsharing.search.dto.LocationDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SearchFacade {

    CarGpsLocationRepository carGpsLocationRepository;

    public Flux<CarDto> findCarsNearby(LocationDto location) {
        return carGpsLocationRepository.findNearestFrom(location)
                .map(gpsLocation -> new CarDto(""));
    }

    public Mono<Void> onCarLocationChanged(String gpsId, LocationDto location) {
        return carGpsLocationRepository.upsert(gpsId, location);
    }
}
