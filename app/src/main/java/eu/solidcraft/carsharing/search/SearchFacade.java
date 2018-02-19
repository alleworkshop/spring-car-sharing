package eu.solidcraft.carsharing.search;

import eu.solidcraft.carsharing.availability.AvailabilityFacade;
import eu.solidcraft.carsharing.car.CarsFacade;
import eu.solidcraft.carsharing.car.dto.CarDto;
import eu.solidcraft.carsharing.kernel.Location;
import eu.solidcraft.carsharing.locator.dto.LocationChangeEvent;
import eu.solidcraft.carsharing.search.dto.CarInLocationDto;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SearchFacade {
    @NonNull CarsFacade carsFacade;
    @NonNull AvailabilityFacade availabilityFacade;
    @NonNull CarLocationRepository store;

    public Flux<CarInLocationDto> findCarsNearby(Location location) {
        Flux<GpsNearby> gpsesNearby = store.findNearby(location);
        Flux<CarInLocationDto> carsNearby = gpsesNearby.flatMap((gpsLocation) ->
                findCarNearby(gpsLocation));
        Flux<CarInLocationDto> availableCars = carsNearby.filterWhen(carDto ->
                availabilityFacade.isAvailable(carDto.getCarId()));
        return availableCars;
    }

    private Mono<CarInLocationDto> findCarNearby(GpsNearby gpsLocation) {
        Mono<CarDto> carByGps = carsFacade.findCarByGps(gpsLocation.getGpsId());
        return carByGps.map(carDto -> new CarInLocationDto(carDto.getCarId(), gpsLocation.getLocation()));
    }

    @EventListener
    @Async
    public void newPosition(LocationChangeEvent locationChange) {
        GpsLocation gpsLocation = new GpsLocation(locationChange.getGpsId(), locationChange.getLocation());
        store.save(gpsLocation);
    }
}