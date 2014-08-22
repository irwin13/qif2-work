package id.co.quadras.qif.engine.core;

/**
 * @author irwin Timestamp : 12/05/2014 17:50
 */
public final class QifConstants {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PARAMETER_MAP = "parameterMap";

    public static final String QIF_EVENT_ID = "qifEventId";

    public static final String HTTP_PATH = "httpPath";
    public static final String HTTP_BODY = "httpBody";

    public static final int DEFAULT_LOG_FETCH = 10;
    public static final int DEFAULT_LOG_INTERVAL = 10;

    public static final long ONE_SECOND = 1000;
    public static final long ONE_MINUTE = ONE_SECOND * 60;
    public static final long ONE_HOUR = ONE_MINUTE * 60;

    public static final String MYBATIS_JDBC = "JDBC";
    public static final String MYBATIS_MANAGED = "MANAGED";
}
