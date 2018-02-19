package eu.solidcraft.carsharing.search;

import eu.solidcraft.carsharing.car.CarsFacade;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class SearchConfiguration {
    public SearchFacade facade(CarsFacade carsFacade) {
        CarLocationRepository store = new BruteForceCarLocationRepository();
        return new SearchFacade(carsFacade, store);
    }
}
