package id.co.quadras.qif.model.vo.adapter;

/**
 * @author irwin Timestamp : 22/05/2014 18:57
 */
public enum AdapterEmail {

    SMTP_HOST("smtp_host"),
    SMTP_PORT("smtp_port"),
    SMTP_USER("smtp_user"),
    SMTP_PASSWORD("smtp_password"),
    SMTP_METHOD("smtp_method"),
    SMTP_METHOD_SSL("smtp_method_ssl"),
    SMTP_METHOD_TLS("smtp_method_tls"),
    SMTP_METHOD_PLAIN("smtp_method_plain");

    private final String name;

    AdapterEmail(String name) {
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
