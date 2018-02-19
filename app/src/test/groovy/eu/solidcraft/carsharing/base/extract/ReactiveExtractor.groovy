package eu.solidcraft.carsharing.base.extract

import reactor.core.publisher.Flux

class ReactiveExtractor {
    static <T> List<T> extract(Flux<T> flux) {
        flux.log()
        return flux.collectList()
                .block()
    }
}
