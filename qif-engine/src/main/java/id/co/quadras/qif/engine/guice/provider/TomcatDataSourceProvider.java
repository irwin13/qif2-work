package id.co.quadras.qif.engine.guice.provider;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Provider;
import id.co.quadras.qif.engine.jaxb.Qif;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import javax.sql.DataSource;

/**
 * @author irwin Timestamp : 11/06/2014 17:03
 */
public class TomcatDataSourceProvider implements Provider<DataSource> {

    private final Qif qifConfig;

    @Inject
    public TomcatDataSourceProvider(Qif qifConfig) {
        this.qifConfig = qifConfig;
    }

    @Override
    public DataSource get() {
        DataSource dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
        PoolProperties poolProperties = new PoolProperties();
        setProperties(poolProperties);
        ((org.apache.tomcat.jdbc.pool.DataSource) dataSource).setPoolProperties(poolProperties);
        return dataSource;
    }

    private void setProperties(PoolProperties poolProperties) {

        poolProperties.setUrl(qifConfig.getDatabaseRepository().getDataSource().getUrl());
        poolProperties.setDriverClassName(qifConfig.getDatabaseRepository().getDataSource().getDriver());
        poolProperties.setUsername(qifConfig.getDatabaseRepository().getDataSource().getUsername());
        poolProperties.setPassword(qifConfig.getDatabaseRepository().getDataSource().getPassword());

        poolProperties.setValidationQuery(qifConfig.getDatabaseRepository().getDataSource().getValidationQuery());

        poolProperties.setTestOnBorrow(Boolean.valueOf(qifConfig.getDatabaseRepository().getDataSource().getTestOnBorrow()));
        poolProperties.setJmxEnabled(Boolean.valueOf(qifConfig.getDatabaseRepository().getDataSource().getJmxEnabled()));
        poolProperties.setTestWhileIdle(Boolean.valueOf(qifConfig.getDatabaseRepository().getDataSource().getTestWhileIdle()));
        poolProperties.setTestOnReturn(Boolean.valueOf(qifConfig.getDatabaseRepository().getDataSource().getTestOnReturn()));

        poolProperties.setInitialSize(Integer.valueOf(qifConfig.getDatabaseRepository().getDataSource().getInitialSize()));
        poolProperties.setMaxActive(Integer.valueOf(qifConfig.getDatabaseRepository().getDataSource().getMaxActive()));
        poolProperties.setMaxIdle(Integer.valueOf(qifConfig.getDatabaseRepository().getDataSource().getMaxIdle()));
        poolProperties.setMinIdle(Integer.valueOf(qifConfig.getDatabaseRepository().getDataSource().getMinIdle()));

        poolProperties.setMinEvictableIdleTimeMillis(Integer.valueOf(qifConfig.getDatabaseRepository().getDataSource().getMinEvictableIdleTimeMillis()));

        poolProperties.setValidationInterval(Integer.valueOf(qifConfig.getDatabaseRepository().getDataSource().getValidationInterval()));
        poolProperties.setTimeBetweenEvictionRunsMillis(Integer.valueOf(qifConfig.getDatabaseRepository().getDataSource().getTimeBetweenEvictionRunsMillis()));

        String maxWait = qifConfig.getDatabaseRepository().getDataSource().getMaxWait();
        if (!Strings.isNullOrEmpty(maxWait)) {
            poolProperties.setMaxWait(Integer.valueOf(maxWait));
        }

        poolProperties.setLogAbandoned(Boolean.valueOf(qifConfig.getDatabaseRepository().getDataSource().getLogAbandoned()));
        poolProperties.setRemoveAbandoned(Boolean.valueOf(qifConfig.getDatabaseRepository().getDataSource().getRemoveAbandoned()));
        poolProperties.setRemoveAbandonedTimeout(Integer.valueOf(qifConfig.getDatabaseRepository().getDataSource().getRemoveAbandonedTimeout()));

        poolProperties.setJdbcInterceptors(qifConfig.getDatabaseRepository().getDataSource().getJdbcInterceptors());
    }
}
