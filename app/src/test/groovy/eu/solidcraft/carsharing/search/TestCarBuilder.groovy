package eu.solidcraft.carsharing.search

import eu.solidcraft.carsharing.search.dto.LocationDto

class TestCarBuilder {
    String gpsId
    String name
    LocationDto currentLocation

    static DEFAULTS = [
            name           : 'aCar',
            currentLocation: new LocationDto(0, 0)
    ]

    static TestCarBuilder aCar(Map properties) {
        properties = DEFAULTS + properties

        new TestCarBuilder(gpsId: UUID.randomUUID().toString(),
                name: properties.name,
                currentLocation: properties.currentLocation)
    }
}
