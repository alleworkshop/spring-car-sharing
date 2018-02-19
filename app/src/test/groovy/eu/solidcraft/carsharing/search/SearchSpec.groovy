package eu.solidcraft.carsharing.search

import eu.solidcraft.carsharing.car.CarsFacade
import eu.solidcraft.carsharing.car.dto.CarDto
import eu.solidcraft.carsharing.kernel.CarId
import eu.solidcraft.carsharing.kernel.GpsId
import eu.solidcraft.carsharing.kernel.Location
import eu.solidcraft.carsharing.locator.dto.LocationChangeEvent
import eu.solidcraft.carsharing.search.dto.CarInLocationDto
import reactor.core.publisher.Mono
import spock.lang.Specification

import static eu.solidcraft.carsharing.base.extract.ReactiveExtractor.extract

class SearchSpec extends Specification {
    Location userLocation = new Location(40.741895, -73.989308)
    GpsId gpsId = new GpsId("tesla3gps")
    CarId carId = new CarId("myTesla3")
    CarsFacade carsFacade = Stub()
    SearchFacade searchFacade = new SearchConfiguration().facade(carsFacade)

    def "should return location of nearest car"() {
        given: "there is a car parked nearby"
            searchFacade.newPosition(new LocationChangeEvent(userLocation, gpsId))

        and: "gpsId points to tesla"
            carsFacade.findCarByGps(gpsId) >> Mono.just(new CarDto(carId))

        when: "user searches for cars nearby by sending his location (lon/lat)"
            List<CarInLocationDto> carsFound =
                    extract(searchFacade.findCarsNearby(userLocation))

        then: "system returns tesla in location nearby"
            CarInLocationDto foundCar = carsFound.first()
            foundCar.carId == carId
            foundCar.getLocation() == userLocation
    }

    def "should return locations of nearest cars"() {

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
