package eu.solidcraft.carsharing.base

import eu.solidcraft.carsharing.CarSharingApplication
import groovy.transform.TypeChecked
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.reactive.context.ReactiveWebApplicationContext
import org.springframework.test.web.reactive.server.WebTestClient
import spock.lang.Specification

@SpringBootTest(
        classes = [CarSharingApplication],
        webEnvironment = SpringBootTest.WebEnvironment.MOCK)
//@AutoConfigureWireMock(port = 0)
@TypeChecked
abstract class MvcIntegrationSpec extends Specification {

    @Autowired
    protected ReactiveWebApplicationContext webApplicationContext

    WebTestClient webClient

    void setup() {
        webClient = WebTestClient.bindToApplicationContext(webApplicationContext).build()
    }
}
