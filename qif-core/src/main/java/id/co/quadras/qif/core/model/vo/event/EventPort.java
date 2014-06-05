package id.co.quadras.qif.core.model.vo.event;

/**
 * @author irwin Timestamp : 12/05/2014 18:48
 */
public enum EventPort {
    PORT("port");

    private final String name;

    EventPort(String name) {
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
