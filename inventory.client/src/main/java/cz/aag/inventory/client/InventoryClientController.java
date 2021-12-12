package cz.aag.inventory.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;

@RestController
public class InventoryClientController {
    private static final Logger logger = LoggerFactory.getLogger(InventoryClientController.class);

    private final Consumer<OrderEvent> orderEventConsumer;
    private int counter = 1;

    @Autowired
    public InventoryClientController(Consumer<OrderEvent> orderEventConsumer) {
        super();
        this.orderEventConsumer = orderEventConsumer;
    }

    @GetMapping("/produce")
    public void produce() {
        logger.info("producing event");
        orderEventConsumer.accept(new OrderEvent("X"));
    }
}
