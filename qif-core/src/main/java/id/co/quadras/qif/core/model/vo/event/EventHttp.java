package id.co.quadras.qif.core.model.vo.event;

/**
 * @author irwin Timestamp : 06/06/2014 19:20
 */
public enum EventHttp {

    HTTP_PATH("http_path"),
    HTTP_METHOD("http_method");

    private final String name;

    EventHttp(String name) {
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
