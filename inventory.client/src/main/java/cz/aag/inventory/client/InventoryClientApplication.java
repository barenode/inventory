package cz.aag.inventory.client;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@SpringBootApplication
public class InventoryClientApplication {
	private static final Logger logger = LoggerFactory.getLogger(InventoryClientApplication.class);

	@Bean
	Sinks.Many<OrderEvent> orderEventSink() {
		return Sinks.many().multicast().directAllOrNothing();//.onBackpressureBuffer();
	}

	@Bean
	Supplier<Flux<OrderEvent>> orderEventProducer(Sinks.Many<OrderEvent> orderEventSink) {
		return orderEventSink::asFlux;
	}

	@Bean
	Consumer<OrderEvent> orderEventConsumer(Sinks.Many<OrderEvent> orderEventSink) {
		return (e) ->  {
			logger.info("emiting event ...");
			orderEventSink.emitNext(e, (signalType, emission) -> {
				logger.info("signalType [" + signalType + "], emission: " + emission);
				return false;
			});
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(InventoryClientApplication.class, args);
	}
}
