package id.co.quadras.qif.model.vo.adapter;

/**
 * @author irwin Timestamp : 12/05/2014 18:49
 */
public enum AdapterFtp {

    HOST("host"),
    PORT("port"),
    USER("user"),
    PASSWORD("password"),
    OVERWRITE("overwrite"),
    FOLDER("folder");

    private final String name;

    AdapterFtp(String name) {
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
