package id.co.quadras.qif.engine.config;

import com.google.common.base.Objects;

/**
 * @author irwin Timestamp : 22/08/2014 16:05
 */
public class JndiDataSource {

    private String jndiName;
    private String initialContext;
    private boolean closeConnection;

    public String getJndiName() {
        return jndiName;
    }

    public void setJndiName(String jndiName) {
        this.jndiName = jndiName;
    }

    public String getInitialContext() {
        return initialContext;
    }

    public void setInitialContext(String initialContext) {
        this.initialContext = initialContext;
    }

    public boolean isCloseConnection() {
        return closeConnection;
    }

    public void setCloseConnection(boolean closeConnection) {
        this.closeConnection = closeConnection;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("jndiName", jndiName)
                .add("initialContext", initialContext)
                .add("closeConnection", closeConnection)
                .toString();
    }
}
