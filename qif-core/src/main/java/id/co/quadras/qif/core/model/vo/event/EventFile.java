package id.co.quadras.qif.core.model.vo.event;

/**
 * @author irwin Timestamp : 12/05/2014 18:45
 */
public enum EventFile {

    FOLDER("folder"),
    END_WITH("end_with"),
    MAX_FETCH("max_fetch"),
    DELETE_AFTER_READ("delete_after_read"),
    LAST_MODIFIED_INTERVAL_SECONDS("last_modified_interval_seconds");

    private final String name;

    EventFile(String name) {
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
