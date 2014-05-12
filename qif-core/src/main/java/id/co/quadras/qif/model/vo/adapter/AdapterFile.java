package id.co.quadras.qif.model.vo.adapter;

/**
 * @author irwin Timestamp : 12/05/2014 18:50
 */
public enum  AdapterFile {

    FILE_NAME("file_name"),
    OVERWRITE("overwrite"),
    FOLDER("folder");

    private final String name;

    AdapterFile(String name) {
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
