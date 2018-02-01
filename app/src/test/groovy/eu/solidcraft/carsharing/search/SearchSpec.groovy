package eu.solidcraft.carsharing.search

import eu.solidcraft.carsharing.search.dto.CarDto
import eu.solidcraft.carsharing.search.dto.LocationDto
import spock.lang.Specification

import static TestCarBuilder.aCar
import static org.assertj.core.api.Assertions.assertThat

class SearchSpec extends Specification {
    SearchFacade searchFacade = SearchTestConfiguration.facade()
    FakeCarsCatalog carsCatalog = SearchTestConfiguration.carsCatalog()

    LocationDto userLocation = new LocationDto(40.741895, -73.989308)

    def setup() {
        carsCatalog.clear()
    }

    def "should return location of nearest car"() {
        given:
            carsAreParked([
                    aCar(name: 'Jaguar', currentLocation: userLocation.shift(3, 1)),
                    aCar(name: 'Mercedes', currentLocation: userLocation.shift(1, 0)),
                    aCar(name: 'BMW', currentLocation: userLocation.shift(1, 2))])

        when:
            List<CarDto> carsFound = searchFacade.findCarsNearby(userLocation)
                    .collectList().block()

        then: "system returns a nearest car"
            carsFound.name == ['Mercedes']
    }

    def "Cars at the same exact distance"() {
        given: "there are two cars in exactly same distance"
            carsAreParked([
                    aCar(name: 'Jaguar', currentLocation: userLocation.shift(1, 1)),
                    aCar(name: 'Mercedes', currentLocation: userLocation.shift(1, 1)),
                    aCar(name: 'BMW', currentLocation: userLocation.shift(10, 10))])

        when: "user searches for cars nearby by sending his location (lon/lat)"
            List<CarDto> carsFound = searchFacade.findCarsNearby(userLocation)
                    .collectList().block()

        then: "system returns information about both cars"
            assertThat(carsFound.name).containsExactlyInAnyOrder('Jaguar', 'Mercedes')
    }

    def "no cars nearby"() {
        given: "there are no cars within 10km radius"
        when: "user searches for cars nearby by sending his location (lon/lat)"
        then: "system returns information that there are no cars nearby"
    }

    void carsAreParked(List<TestCarBuilder> cars) {
        cars.forEach {
            carsCatalog.save(it.gpsId, it.name)
            searchFacade.onCarLocationChanged(it.gpsId, it.currentLocation).block()
        }
    }
}
