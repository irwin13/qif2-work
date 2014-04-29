package id.co.quadras.qif;

/**
 * @author irwin Timestamp : 24/04/2014 20:26
 */
public interface QifActivity {

    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";

    public String activityName();
    public String activityType();
}
