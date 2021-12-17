package cz.aag.inventory;

import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

@SpringBootApplication
public class InventoryApplication {
	private static final int number = 1;
	private static final Logger logger = LoggerFactory.getLogger(InventoryApplication.class);

	@Bean
	Function<OrderEvent, OrderEvent> proc() {
		return (e) -> {
			logger.info("processing event " + e);
			throw new RuntimeException();
			//return e;
		};
	}

	@Bean
	Sinks.Many<OrderEvent> sink() {
		return Sinks.many().multicast().onBackpressureBuffer();
	}

	@Bean
	Supplier<Flux<OrderEvent>> producer(Sinks.Many<OrderEvent> sink) {
		return sink::asFlux;
	}

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}
}
