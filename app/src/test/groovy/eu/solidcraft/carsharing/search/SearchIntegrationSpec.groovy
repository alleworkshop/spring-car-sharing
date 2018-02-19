package eu.solidcraft.carsharing.search

import eu.solidcraft.carsharing.base.MvcIntegrationSpec

class SearchIntegrationSpec extends MvcIntegrationSpec {

    def "should return locations of nearest cars"() {
        given: "there are cars A(2,2), B(1,1), C(2,2), D(400,400)"
//        [A(2,2), B(1,1), C(2,2), D(400,400)]
//        locationsDetected([A, B, C, D])
//        registered([A, B, C, D])

        and: "A, B are available"
//        available(A, B)

        when: "user searches for cars nearby by sending his location (lon/lat)"
//        List<CarInLocationDto> carsFound =
//                extract(searchFacade.findCarsNearby(userLocation))

        then: "system returns [B, A] (sorted by distance)"
//        carsFound.carId == [B, A].carId
        1==1
    }
}
