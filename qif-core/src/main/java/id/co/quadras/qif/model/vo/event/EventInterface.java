package id.co.quadras.qif.model.vo.event;

/**
 * @author irwin Timestamp : 12/05/2014 17:28
 */
public enum EventInterface {

    HTTP_REST("http_rest"),
    WEB_SERVICE_SOAP("webservice_soap"),
    FTP("ftp"),
    FILE("file"),
    JMS_QUEUE("jms_queue"),
    ORACLE_ADVANCE_QUEUE("oaq");

    private final String name;

    EventInterface(String name) {
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
