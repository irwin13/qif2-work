package id.co.quadras.qif.model.vo.event;

/**
 * @author irwin Timestamp : 12/05/2014 18:19
 */
public enum SchedulerInterval {

    CONCURRENT_EXECUTION("concurrent_execution"),
    INTERVAL("interval");

    private final String name;

    SchedulerInterval(String name) {
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
