package eu.solidcraft.carsharing.kernel;

import lombok.AccessLevel;
import lombok.Value;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Value
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class Location {
    public static Location of(BigDecimal lat, BigDecimal lon) {
        return new Location(lat, lon);
    }

    BigDecimal lat;
    BigDecimal lon;

    public BigDecimal distanceTo(Location other) {
        return BigDecimal.valueOf(Math.sqrt(
                latModule(other).add(lonModule(other)).doubleValue()));
    }

    private BigDecimal latModule(Location other) {
        return (lat.subtract(other.lat)).pow(2);
    }

    private BigDecimal lonModule(Location other) {
        return (lon.subtract(other.lon)).pow(2);
    }
}
