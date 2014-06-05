package id.co.quadras.qif.core.model.vo.event;

/**
 * @author irwin Timestamp : 12/05/2014 17:26
 */
public enum EventType {

    SCHEDULER_CRON("scheduler_cron"),
    SCHEDULER_INTERVAL("scheduler_interval"),
    INCOMING_MESSAGE("incoming_message");

    private final String name;

    EventType(String name) {
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
