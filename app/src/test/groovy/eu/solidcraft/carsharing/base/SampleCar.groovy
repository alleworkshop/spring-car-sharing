package eu.solidcraft.carsharing.base

import eu.solidcraft.carsharing.kernel.CarId
import eu.solidcraft.carsharing.kernel.GpsId
import eu.solidcraft.carsharing.kernel.Location
import groovy.transform.CompileStatic

@CompileStatic
class SampleCar {
    CarId carId;
    GpsId gpsId;
    Location location

    SampleCar(String id) {
        this.carId = new CarId(id)
        gpsId = new GpsId(id)
    }

    SampleCar call(int lat, int lon) {
        location = new Location(BigDecimal.valueOf(lat), BigDecimal.valueOf(lon))
        return this
    }

    static SampleCar car(String id) {
        return new SampleCar(id)
    }

    static SampleCar car() {
        return new SampleCar(UUID.randomUUID().toString())
    }
}