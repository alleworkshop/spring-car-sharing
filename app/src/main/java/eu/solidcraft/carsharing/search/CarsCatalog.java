package eu.solidcraft.carsharing.search;

import eu.solidcraft.carsharing.search.dto.CarDto;
import reactor.core.publisher.Mono;

interface CarsCatalog {

    Mono<CarDto> findCarDetailsByGpsId(String gpsId);
}
