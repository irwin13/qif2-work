package id.co.quadras.qif.core.model.vo.adapter;

/**
 * @author irwin Timestamp : 12/05/2014 17:31
 */
public enum AdapterInterface {

    HTTP_REST("http_rest"),
    WEB_SERVICE_SOAP("webservice_soap"),
    FTP("ftp"),
    FILE("file"),
    JMS_QUEUE("jms_queue"),
    ORACLE_ADVANCE_QUEUE("oaq"),
    EMAIL("email");

    private final String name;

    AdapterInterface(String name) {
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
