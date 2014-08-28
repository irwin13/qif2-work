package id.co.quadras.qif.model.vo.adapter;

/**
 * @author irwin Timestamp : 28/08/2014 14:58
 */
public enum AdapterJmsQueue {

    INITIAL_CONTEXT_FACTORY("initial_context_factory"),
    JNDI_URL_PROVIDER("jndi_url_provider"),
    JNDI_USER("jndi_user"),
    JNDI_PASSWORD("jndi_password"),
    JNDI_CONNECTION_FACTORY("jndi_connection_factory"),
    JNDI_QUEUE("jndi_queue");

    private final String name;

    AdapterJmsQueue(String name) {
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
