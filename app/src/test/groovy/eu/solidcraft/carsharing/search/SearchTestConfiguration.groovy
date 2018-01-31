package eu.solidcraft.carsharing.search


class SearchTestConfiguration {

    SearchFacade facade() {
        new SearchFacade(new FakeCarGpsLocationRepository())
    }
}
