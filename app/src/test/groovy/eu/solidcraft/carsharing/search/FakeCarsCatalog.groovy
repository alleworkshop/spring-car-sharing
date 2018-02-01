package eu.solidcraft.carsharing.search

import eu.solidcraft.carsharing.search.dto.CarDto
import reactor.core.publisher.Mono

class FakeCarsCatalog implements CarsCatalog {

    private final List<CarDto> cars = new LinkedList<>()

    @Override
    Mono<CarDto> findCarDetailsByGpsId(String gpsId) {
        cars.stream()
                .filter({ c -> c.gpsId == gpsId })
                .findFirst()
                .map { c -> Mono.just(c) }
                .orElseGet { -> Mono.empty() }
    }

    void save(String gpsId, String carName) {
        cars.add(new CarDto(carName, gpsId))
    }

    void clear() {
        cars.clear()
    }
}
