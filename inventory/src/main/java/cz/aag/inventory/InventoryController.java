package cz.aag.inventory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Sinks;

@RestController
public class InventoryController {
    private static final Logger logger = LoggerFactory.getLogger(InventoryController.class);

    private final Sinks.Many<OrderEvent> sink;

    @Autowired
    public InventoryController(Sinks.Many<OrderEvent> sink) {
        super();
        this.sink = sink;
    }

    @GetMapping("/send")
    public void get() {
        logger.info("-- send --");
        sink.tryEmitNext(new OrderEvent());
    }
}
