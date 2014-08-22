package id.co.quadras.qif.model.vo.adapter;

/**
 * @author irwin Timestamp : 12/05/2014 18:50
 */
public enum AdapterOaq {

    SCHEMA("schema"),
    QUEUE_NAME("queue_name");

    private final String name;

    AdapterOaq(String name) {
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
