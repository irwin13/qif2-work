package id.co.quadras.qif.engine.guice.provider;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import id.co.quadras.qif.engine.config.QifConfig;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import javax.sql.DataSource;

/**
 * @author irwin Timestamp : 11/06/2014 17:03
 */
@Singleton
public class TomcatDataSourceProvider implements Provider<DataSource> {

    private final QifConfig qifConfig;

    @Inject
    public TomcatDataSourceProvider(QifConfig qifConfig) {
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

        poolProperties.setUrl(qifConfig.getJdbcDataSource().getUrl());
        poolProperties.setDriverClassName(qifConfig.getJdbcDataSource().getDriver());
        poolProperties.setUsername(qifConfig.getJdbcDataSource().getUsername());
        poolProperties.setPassword(qifConfig.getJdbcDataSource().getPassword());

        poolProperties.setValidationQuery(qifConfig.getJdbcDataSource().getValidationQuery());

        poolProperties.setTestOnBorrow(qifConfig.getJdbcDataSource().isTestOnBorrow());
        poolProperties.setJmxEnabled(qifConfig.getJdbcDataSource().isJmxEnabled());
        poolProperties.setTestWhileIdle(qifConfig.getJdbcDataSource().isTestWhileIdle());
        poolProperties.setTestOnReturn(qifConfig.getJdbcDataSource().isTestOnReturn());

        poolProperties.setInitialSize(Integer.valueOf(qifConfig.getJdbcDataSource().getInitialSize()));
        poolProperties.setMaxActive(Integer.valueOf(qifConfig.getJdbcDataSource().getMaxActive()));
        poolProperties.setMaxIdle(Integer.valueOf(qifConfig.getJdbcDataSource().getMaxIdle()));
        poolProperties.setMinIdle(Integer.valueOf(qifConfig.getJdbcDataSource().getMinIdle()));

        poolProperties.setMinEvictableIdleTimeMillis(Integer.valueOf(qifConfig.getJdbcDataSource().getMinEvictableIdleTimeMillis()));

        poolProperties.setValidationInterval(Integer.valueOf(qifConfig.getJdbcDataSource().getValidationInterval()));
        poolProperties.setTimeBetweenEvictionRunsMillis(Integer.valueOf(qifConfig.getJdbcDataSource().getTimeBetweenEvictionRunsMillis()));

        String maxWait = qifConfig.getJdbcDataSource().getMaxWait();
        if (!Strings.isNullOrEmpty(maxWait)) {
            poolProperties.setMaxWait(Integer.valueOf(maxWait));
        }

        poolProperties.setLogAbandoned(qifConfig.getJdbcDataSource().isLogAbandoned());
        poolProperties.setRemoveAbandoned(qifConfig.getJdbcDataSource().isRemoveAbandoned());
        poolProperties.setRemoveAbandonedTimeout(qifConfig.getJdbcDataSource().getRemoveAbandonedTimeout());

        poolProperties.setJdbcInterceptors(qifConfig.getJdbcDataSource().getJdbcInterceptors());
    }
}
