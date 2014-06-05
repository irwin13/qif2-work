package id.co.quadras.qif.core.model.vo.event;

/**
 * @author irwin Timestamp : 12/05/2014 18:19
 */
public enum SchedulerCron {

    CONCURRENT_EXECUTION("concurrent_execution"),
    CRON_EXPRESSION("cron_expression");

    private final String name;

    SchedulerCron(String name) {
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
