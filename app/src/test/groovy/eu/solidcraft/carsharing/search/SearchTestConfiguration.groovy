package eu.solidcraft.carsharing.search

class SearchTestConfiguration {

    FakeCarsCatalog carsCatalog = new FakeCarsCatalog()

    SearchFacade facade() {
        new SearchFacade(new FakeCarGpsLocationRepository(), carsCatalog)
    }

    FakeCarsCatalog carsCatalog() {
        carsCatalog
    }
}
