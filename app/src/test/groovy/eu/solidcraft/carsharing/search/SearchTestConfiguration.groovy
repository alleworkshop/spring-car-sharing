package eu.solidcraft.carsharing.search

class SearchTestConfiguration {

    private static SearchConfiguration configuration = new SearchConfiguration()

    private static FakeCarsCatalog carsCatalog = new FakeCarsCatalog()

    static SearchFacade facade() {
        configuration.facade(new FakeCarGpsLocationRepository(), carsCatalog())
    }

    static FakeCarsCatalog carsCatalog() {
        carsCatalog
    }
}
