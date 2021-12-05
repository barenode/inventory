package cz.aag.inventory;

import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryApplication {

	@Bean
	Function<OrderEvent, OrderEvent> proc() {
		return Function.identity();
	}

	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
	}
}
