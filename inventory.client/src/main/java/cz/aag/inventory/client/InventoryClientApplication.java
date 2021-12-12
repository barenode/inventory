package cz.aag.inventory.client;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@SpringBootApplication
public class InventoryClientApplication {

	@Bean
	Sinks.Many<OrderEvent> orderEventSink() {
		return Sinks.many().multicast().onBackpressureBuffer();
	}

	@Bean
	Supplier<Flux<OrderEvent>> orderEventProducer(Sinks.Many<OrderEvent> orderEventSink) {
		return orderEventSink::asFlux;
	}

	@Bean
	Consumer<OrderEvent> orderEventConsumer(Sinks.Many<OrderEvent> orderEventSink) {
		return (e) ->  orderEventSink.emitNext(e, Sinks.EmitFailureHandler.FAIL_FAST);
	}

	public static void main(String[] args) {
		SpringApplication.run(InventoryClientApplication.class, args);
	}
}
