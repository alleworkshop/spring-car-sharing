package eu.solidcraft.carsharing.search

import eu.solidcraft.carsharing.search.dto.LocationDto
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

//@CompileStatic
class FakeCarGpsLocationRepository implements CarGpsLocationRepository {

    private final Map<String, LocationDto> carLocations = new HashMap<>()

    @Override
    Mono<Void> upsert(String gpsId, LocationDto location) {
        Mono.fromRunnable({ -> carLocations.merge(gpsId, location, { prev, current -> current }) })
    }

    @Override
    Flux<CarGpsLocationRepository.CarGpsLocation> findNearestFrom(LocationDto location) {

        carLocations.values()
                .stream()
                .map { loc -> location.distanceTo(loc) }
                .min(Comparator.naturalOrder())
                .map { filterLocations(it, location) }
                .orElseGet { -> Flux.empty() }
    }

    Flux<CarGpsLocationRepository.CarGpsLocation> filterLocations(BigDecimal minDistance, LocationDto location) {
        Flux.fromIterable(carLocations.entrySet())
                .filter { e -> e.value.distanceTo(location) == minDistance }
                .map { e -> new CarGpsLocationRepository.CarGpsLocation(e.key, e.value) }
    }
}
