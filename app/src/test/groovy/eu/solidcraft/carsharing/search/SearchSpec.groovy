package eu.solidcraft.carsharing.search

import eu.solidcraft.carsharing.search.dto.CarDto
import eu.solidcraft.carsharing.search.dto.LocationDto
import spock.lang.Specification

class SearchSpec extends Specification {
    BigDecimal lat = 40.741895
    BigDecimal lon = -73.989308
    SearchFacade searchFacade = new SearchConfiguration().facade()


    def "should return location of nearest car"() {
        given: "there are cars parked nearby"

        when: "user searches for cars nearby by sending his location (lon/lat)"
            List<CarDto> carsFound = searchFacade.findCarsNearby(new LocationDto(lat, lon))
                .collectList().block()

        then: "system returns list of nearby cars"
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
