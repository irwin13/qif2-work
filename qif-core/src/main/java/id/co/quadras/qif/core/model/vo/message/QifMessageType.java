package id.co.quadras.qif.core.model.vo.message;

/**
 * @author irwin Timestamp : 11/07/2014 10:42
 */
public enum QifMessageType {
    TEXT("text"), BINARY("binary");

    private final String name;

    QifMessageType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
