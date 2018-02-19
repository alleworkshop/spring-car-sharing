package eu.solidcraft.carsharing.car;

import eu.solidcraft.carsharing.car.dto.CarDto;
import eu.solidcraft.carsharing.kernel.GpsId;
import reactor.core.publisher.Mono;

public class CarsFacade {
    public Mono<CarDto> findCarByGps(GpsId gpsId) {
        return null;
    }
}
