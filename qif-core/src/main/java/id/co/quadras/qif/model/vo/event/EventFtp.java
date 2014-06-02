package id.co.quadras.qif.model.vo.event;

/**
 * @author irwin Timestamp : 02/06/2014 13:42
 */
public enum EventFtp {

    HOST("host"),
    PORT("port"),
    USER("user"),
    PASSWORD("password"),
    FOLDER("folder"),
    END_WITH("end_with"),
    MAX_FETCH("max_fetch"),
    LAST_MODIFIED_INTERVAL_SECONDS("last_modified_interval_seconds");;

    private final String name;

    EventFtp(String name) {
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
