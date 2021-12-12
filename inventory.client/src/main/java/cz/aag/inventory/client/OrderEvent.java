package cz.aag.inventory.client;

public class OrderEvent {

    private String id;

    public OrderEvent() {
        super();
    }

    public OrderEvent(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
