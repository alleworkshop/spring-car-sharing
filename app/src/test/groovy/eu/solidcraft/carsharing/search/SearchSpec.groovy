package eu.solidcraft.carsharing.search

import eu.solidcraft.carsharing.search.dto.CarDto
import eu.solidcraft.carsharing.search.dto.LocationDto
import spock.lang.Specification

class SearchSpec extends Specification {
    SearchTestConfiguration config = new SearchTestConfiguration()
    SearchFacade searchFacade = config.facade()
    FakeCarsCatalog carsCatalog = config.carsCatalog()

    LocationDto userLocation = new LocationDto(40.741895, -73.989308)

    def setup() {
        carsCatalog.clear()
    }

    def "should return location of nearest car"() {
        given:
            carsAreParked([
                    new TestCar(gpsId: '1', name: 'Jaguar', currentLocation: userLocation.shift(3, 1)),
                    new TestCar(gpsId: '2', name: 'Mercedes', currentLocation: userLocation.shift(1, 0)),
                    new TestCar(gpsId: '3', name: 'BMW', currentLocation: userLocation.shift(1, 2))]) //TODO: builders

        when:
            List<CarDto> carsFound = searchFacade.findCarsNearby(userLocation)
                    .collectList().block()

        then: "system returns a nearest car"
            carsFound.name == ['Mercedes']
    }

    void carsAreParked(List<TestCar> cars) {
        cars.forEach {
            carsCatalog.save(it.gpsId, it.name)
            searchFacade.onCarLocationChanged(it.gpsId, it.currentLocation).block()
        }
    }

    def "no cars nearby"() {
        given: "there are no cars within 10km radius"
        when: "user searches for cars nearby by sending his location (lon/lat)"
        then: "system returns information that there are no cars nearby"
    }

    def "Cars at the same exact distance"() {
        given: "there are two cars in exactly 1km distance"
        when: "user searches for cars nearby by sending his location (lon/lat)"
        then: "system returns information about both cars"
    }

}
