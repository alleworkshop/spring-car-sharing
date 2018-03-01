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
import static eu.solidcraft.carsharing.base.extract.ReactiveExtractor.toList

class SearchSpec extends Specification {
    CarsFacade carsFacade = Stub()
    AvailabilityFacade availabilityFacade = Stub()
    SearchFacade searchFacade = new SearchConfiguration().facade(carsFacade, availabilityFacade)
    SampleCar A, B, C, D
    List<CarInLocationDto> carsFound = []

    def "should return locations of nearest cars"() {
        given: "there are cars A(2,2), B(1,1), C(2,2), D(400,400)"
            A = car(2,2); B = car(1,1); C = car(2,2); D = car(400,400)
            locationsDetected(A, B, C, D)
            registered(A, B, C, D)

        and: "A, B are available"
            available(A, B)

        when: "user searches for cars nearby by sending his location (lat/lon)"
            carsFound = findCarsNearby(0, 0)

        then: "system returns [B, A] (sorted by distance)"
            carsFound.carId == [B, A].carId
    }

    def "car not registered"() {
        given: "there are cars A(1,1), B(1,1)"
            A = car(1,1); B = car(1,1)
            locationsDetected(A, B)

        and: "only A is registered"
            registered(A)

        and: "A, B are available"
            available(A, B)

        when: "user searches for cars nearby by sending his location (lon/lat)"
            carsFound = findCarsNearby(0, 0)

        then: "system returns only A because B is not registered"
            carsFound.carId == [A].carId
    }

    def "no cars nearby"() {
        given: "there are no cars within 4km radius"
            A = car(5,5); B = car(-5,-5)
            locationsDetected(A, B)
            registered(A, B)

        and: "A, B are available"
            available(A, B)

        when: "user searches for cars nearby by sending his location (lon/lat)"
            carsFound = findCarsNearby(0, 0)

        then: "system returns information that there are no cars nearby"
            carsFound.isEmpty()
    }

    def "no cars available"() {
        given: "there are no cars within 4km radius"
            A = car(1,1)
            locationsDetected(A)
            registered(A)

        and: "no car is available"
            available()

        when: "user searches for cars nearby by sending his location (lon/lat)"
            carsFound = findCarsNearby(0, 0)

        then: "system returns information that there are no cars nearby"
            carsFound.isEmpty()
    }

    def "Cars at the same exact distance"() {
        given: "there are two cars in exactly 1km distance"
            A = car(1,1); B = car(1,1)
            locationsDetected(A, B)
            registered(A, B)

        and: "A, B are available"
            available(A, B)

        when: "user searches for cars nearby by sending his location (lon/lat)"
            carsFound = findCarsNearby(0, 0)

        then: "system returns information about both cars"
            carsFound.carId.containsAll([A, B].carId)
    }

    private List<CarInLocationDto> findCarsNearby(BigDecimal lat, BigDecimal lon) {
        return toList(searchFacade.findCarsNearby(Location.of(lat, lon)))
    }

    private void available(SampleCar... sampleCars) {
        sampleCars.each {
            availabilityFacade.isAvailable(it.carId) >> Mono.just(true)
        }
        availabilityFacade.isAvailable(_) >> Mono.just(false)
    }

    private void locationsDetected(SampleCar... sampleCars) {
        sampleCars.each {
            searchFacade.newPosition(new LocationChangeEvent(it.location, it.gpsId))
        }
    }

    private void registered(SampleCar... sampleCars) {
        sampleCars.each {
            carsFacade.findCarByGps(it.gpsId) >> Mono.just(new CarDto(it.carId))
        }
        carsFacade.findCarByGps(_) >> Mono.empty()
    }
}
