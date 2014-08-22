package id.co.quadras.qif.engine.core;

/**
 * @author irwin Timestamp : 24/04/2014 20:26
 */
public interface QifActivity {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    public String activityName();
    public String activityType();
}
