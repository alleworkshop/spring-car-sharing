package eu.solidcraft.carsharing.search;

import eu.solidcraft.carsharing.kernel.GpsId;
import eu.solidcraft.carsharing.kernel.Location;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

interface CarLocationRepository {
    Flux<GpsNearby> findNearby(Location location);
    void save(GpsLocation gpsLocation);
}

class BucketCarLocationRepository implements CarLocationRepository {
    @Override
    public Flux<GpsNearby> findNearby(Location location) {
        return null; //TODO
    }

    @Override
    public void save(GpsLocation gpsLocation) {
        //TODO
    }
}

class BruteForceCarLocationRepository implements CarLocationRepository {
    Map<GpsId, Location> store = new ConcurrentHashMap<>();

    @Override
    public Flux<GpsNearby> findNearby(Location userLocation) {
        Stream<GpsNearby> gpsLocationStream = store.entrySet()
                .stream()
                .map((Map.Entry<GpsId, Location> entry) ->
                        new GpsNearby(entry.getKey(), entry.getValue(), userLocation.distanceTo(entry.getValue())));
        Stream<GpsNearby> closest10Gpses = gpsLocationStream.sorted(Comparator.comparing(GpsNearby::getDistanceFormUser))
                .limit(10);
        return Flux.fromStream(closest10Gpses);
    }

    @Override
    public void save(GpsLocation gpsLocation) {
        store.put(gpsLocation.getGpsId(), gpsLocation.getLocation());
    }

}

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class GpsLocation {
    @Getter GpsId gpsId;
    @Getter Location location;
}

@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class GpsNearby {
    @Getter GpsId gpsId;
    @Getter Location location;
    @Getter BigDecimal distanceFormUser;
}