package eu.solidcraft.carsharing.search

import eu.solidcraft.carsharing.availability.AvailabilityFacade
import eu.solidcraft.carsharing.base.SampleCar
import eu.solidcraft.carsharing.car.CarsFacade
import eu.solidcraft.carsharing.car.dto.CarDto
import eu.solidcraft.carsharing.kernel.Location
import eu.solidcraft.carsharing.locator.dto.LocationChangeEvent
import eu.solidcraft.carsharing.search.dto.CarInLocationDto
import reactor.core.publisher.Mono
import spock.lang.Specification

import static eu.solidcraft.carsharing.base.SampleCar.car
import static eu.solidcraft.carsharing.base.extract.ReactiveExtractor.extract

class SearchSpec extends Specification {
    CarsFacade carsFacade = Stub()
    AvailabilityFacade availabilityFacade = Stub()
    SearchFacade searchFacade = new SearchConfiguration().facade(carsFacade, availabilityFacade)
    SampleCar A = car(), B = car(), C = car(), D = car()

    def "should return locations of nearest cars"() {
        given: "there are cars A(2,2), B(1,1), C(2,2), D(400,400)"
            [A(2,2), B(1,1), C(2,2), D(400,400)]
            locationsDetected([A, B, C, D])
            registered([A, B, C, D])

        and: "user is at location (0,0)"
            Location userLocation = new Location(0, 0)

        and: "A, B are available"
            available(A, B)

        when: "user searches for cars nearby by sending his location (lon/lat)"
            List<CarInLocationDto> carsFound =
                extract(searchFacade.findCarsNearby(userLocation))

        then: "system returns [B, A] (sorted by distance)"
            carsFound.carId == [B, A].carId
    }

    private void available(SampleCar... sampleCars) {
        sampleCars.each {
            availabilityFacade.isAvailable(it.carId) >> Mono.just(true)
        }
        availabilityFacade.isAvailable(_) >> Mono.just(false)
    }

    private void locationsDetected(List<SampleCar> sampleCars) {
        sampleCars.each {
            searchFacade.newPosition(new LocationChangeEvent(it.location, it.gpsId))
        }
    }

    private void registered(List<SampleCar> sampleCars) {
        sampleCars.each {
            carsFacade.findCarByGps(it.gpsId) >> Mono.just(new CarDto(it.carId))
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
