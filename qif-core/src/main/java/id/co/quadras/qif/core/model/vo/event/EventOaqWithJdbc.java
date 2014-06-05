package id.co.quadras.qif.core.model.vo.event;

/**
 * @author irwin Timestamp : 12/05/2014 18:44
 */
public enum EventOaqWithJdbc {

    TABLE_NAME("table_name"),
    DELETE_AFTER_READ("delete_after_read"),
    MAX_FETCH("max_fetch");

    private final String name;

    EventOaqWithJdbc(String name) {
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
