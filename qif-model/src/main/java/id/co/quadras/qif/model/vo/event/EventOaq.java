package id.co.quadras.qif.model.vo.event;

/**
 * @author irwin Timestamp : 12/05/2014 18:43
 */
public enum EventOaq {

    QUEUE_NAME("queue_name"),
    MAX_FETCH("max_fetch");

    private final String name;

    EventOaq(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

}
