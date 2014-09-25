package id.co.quadras.qif.model.vo.event;

/**
 * @author irwin Timestamp : 19/08/2014 19:58
 */
public enum EventJms {

    INITIAL_CONTEXT_FACTORY("initial_context_factory"),
    JNDI_URL_PROVIDER("jndi_url_provider"),
    JNDI_USER("jndi_user"),
    JNDI_PASSWORD("jndi_password"),
    JNDI_CONNECTION_FACTORY("jndi_connection_factory"),
    JNDI_DESTINATION("jndi_destination"),
    MESSAGE_SELECTOR("message_selector"),
    THREAD_COUNT("thread_count"),
    LISTENER_COUNT("listener_count");

    private final String name;

    EventJms(String name) {
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
