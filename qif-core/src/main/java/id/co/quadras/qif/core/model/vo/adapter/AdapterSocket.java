package id.co.quadras.qif.core.model.vo.adapter;

/**
 * @author irwin Timestamp : 12/05/2014 18:50
 */
public enum AdapterSocket {

    HOST("file_name"),
    PORT("overwrite");

    private final String name;

    AdapterSocket(String name) {
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
