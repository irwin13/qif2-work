package id.co.quadras.qif.ui.dao;

import id.co.quadras.qif.ui.guice.GuiceQifUi;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;
import org.hibernate.service.spi.Configurable;
import org.hibernate.service.spi.Stoppable;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;

/**
 * @author irwin Timestamp : 31/05/13 16:48
 */
public class TomcatDataSourceProvider implements ConnectionProvider, Configurable, Stoppable {

    private Properties properties;

    @Override
    public void configure(Map map) {
        properties = new Properties();
        properties.putAll(map);
    }

    @Override
    public void stop() {
        DataSource dataSource = GuiceQifUi.getInjector().getInstance(DataSource.class);
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).close(true);
    }

    @Override
    public Connection getConnection() throws SQLException {
        DataSource dataSource = GuiceQifUi.getInjector().getInstance(DataSource.class);
        return dataSource.getConnection();
    }

    @Override
    public void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return ConnectionProvider.class.equals(unwrapType) ||
                TomcatDataSourceProvider.class.isAssignableFrom(unwrapType);
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        if (ConnectionProvider.class.equals(unwrapType) ||
                TomcatDataSourceProvider.class.isAssignableFrom(unwrapType)) {
            return (T) this;
        } else {
            throw new UnknownUnwrapTypeException(unwrapType);
        }
    }
}
