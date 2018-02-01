package eu.solidcraft.carsharing.search;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class SearchConfiguration {

    @Bean
    SearchFacade facade() {
        return new SearchFacade(null, null);
    }

    SearchFacade facade(CarGpsLocationRepository carGpsLocationRepository, CarsCatalog carsCatalog) {
        return new SearchFacade(carGpsLocationRepository, carsCatalog);
    }
}
