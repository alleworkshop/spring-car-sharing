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

    SampleCar(BigDecimal lat, BigDecimal lon) {
        String id = UUID.randomUUID().toString()
        carId = new CarId(id)
        gpsId = new GpsId(id)
        location = new Location(lat, lon)
    }

    static SampleCar car(BigDecimal lat, BigDecimal lon) {
        return new SampleCar(lat, lon)
    }
}