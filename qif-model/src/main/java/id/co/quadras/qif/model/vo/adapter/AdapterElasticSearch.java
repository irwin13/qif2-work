package id.co.quadras.qif.model.vo.adapter;

/**
 * @author irwin Timestamp : 01/10/2014 16:50
 */
public enum AdapterElasticSearch {

    CLUSTER_NAME("cluster_name"),
    SERVER_LIST("serverList"),;

    private final String name;

    AdapterElasticSearch(String name) {
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
