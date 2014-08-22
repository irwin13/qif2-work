package id.co.quadras.qif.engine.guice.provider;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.entity.app.AppSetting;
import id.co.quadras.qif.engine.QifException;
import id.co.quadras.qif.engine.config.QifConfig;
import id.co.quadras.qif.engine.core.QifConstants;
import id.co.quadras.qif.engine.sqlmap.*;
import id.co.quadras.qif.engine.sqlmap.app.AppSettingSqlmap;
import id.co.quadras.qif.engine.sqlmap.log.*;
import id.co.quadras.qif.model.entity.*;
import id.co.quadras.qif.model.entity.log.*;
import org.apache.ibatis.logging.slf4j.Slf4jImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.*;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.apache.ibatis.type.JdbcType;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

/**
 * @author irwin Timestamp : 06/06/2014 18:38
 */
public class QifSqlSessionFactoryProvider implements Provider<SqlSessionFactory> {

    private final QifConfig qifConfig;
    private final DataSource dataSource;

    @Inject
    public QifSqlSessionFactoryProvider(QifConfig qifConfig, DataSource dataSource) {
        this.qifConfig = qifConfig;
        this.dataSource = dataSource;
    }

    @Override
    public SqlSessionFactory get() {

        TransactionFactory transactionFactory;
        if (qifConfig.getTransactionManager().equalsIgnoreCase(QifConstants.MYBATIS_JDBC)) {
            transactionFactory = new JdbcTransactionFactory();
        } else if (qifConfig.getTransactionManager().equalsIgnoreCase(QifConstants.MYBATIS_MANAGED)) {
            transactionFactory = new ManagedTransactionFactory();
            Properties properties = new Properties();
            properties.setProperty("closeConnection", String.valueOf(qifConfig.getJndiDataSource().isCloseConnection()));
            transactionFactory.setProperties(properties);
        } else {
            throw new QifException("FATAL : transactionManager type must be JDBC or MANAGED in qif-config.xml");
        }

        Environment environment = new Environment("qif", transactionFactory, dataSource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(getConfiguration(environment));

        return sqlSessionFactory;
    }


    private Configuration getConfiguration(Environment environment) {
        Configuration configuration = new Configuration(environment);

        // boolean properties
        configuration.setCacheEnabled(qifConfig.getDbSetting().isCacheEnabled());
        configuration.setLazyLoadingEnabled(qifConfig.getDbSetting().isLazyLoadingEnabled());
        configuration.setMultipleResultSetsEnabled(qifConfig.getDbSetting().isMultipleResultSetsEnabled());
        configuration.setUseColumnLabel(qifConfig.getDbSetting().isUseColumnLabel());
        configuration.setUseGeneratedKeys(qifConfig.getDbSetting().isUseGeneratedKeys());
        configuration.setSafeRowBoundsEnabled(qifConfig.getDbSetting().isSafeRowBoundsEnabled());
        configuration.setMapUnderscoreToCamelCase(qifConfig.getDbSetting().isMapUnderscoreToCamelCase());

        // enum properties
        configuration.setAutoMappingBehavior(AutoMappingBehavior.valueOf(qifConfig.getDbSetting().getAutoMappingBehavior()));
        configuration.setDefaultExecutorType(ExecutorType.valueOf(qifConfig.getDbSetting().getDefaultExecutorType()));
        configuration.setLocalCacheScope(LocalCacheScope.valueOf(qifConfig.getDbSetting().getLocalCacheScope()));
        configuration.setJdbcTypeForNull(JdbcType.valueOf(qifConfig.getDbSetting().getJdbcTypeForNull()));

        // integer properties
        String defaultStatementTimeout = qifConfig.getDbSetting().getDefaultStatementTimeout();
        if (!Strings.isNullOrEmpty(defaultStatementTimeout)) {
            configuration.setDefaultStatementTimeout(Integer.valueOf(defaultStatementTimeout));
        }

        // string array properties
        String[] lazyLoadTriggerMethodArray = qifConfig.getDbSetting().getLazyLoadTriggerMethods().split(",");
        configuration.setLazyLoadTriggerMethods(new HashSet<String>(Arrays.asList(lazyLoadTriggerMethodArray)));

        // string properties
        configuration.setLogPrefix(qifConfig.getDbSetting().getLogPrefix());

        // fixed properties
        configuration.setLogImpl(Slf4jImpl.class);

        // typeAlias
        configuration.getTypeAliasRegistry().registerAlias("SearchParameter", SearchParameter.class);
        configuration.getTypeAliasRegistry().registerAlias("AppSetting", AppSetting.class);
        configuration.getTypeAliasRegistry().registerAlias("QifCounter", QifCounter.class);

        configuration.getTypeAliasRegistry().registerAlias("QifEventLog", QifEventLog.class);
        configuration.getTypeAliasRegistry().registerAlias("QifEventLogMsg", QifEventLogMsg.class);

        configuration.getTypeAliasRegistry().registerAlias("QifActivityLog", QifActivityLog.class);
        configuration.getTypeAliasRegistry().registerAlias("QifActivityLogData", QifActivityLogData.class);
        configuration.getTypeAliasRegistry().registerAlias("QifActivityLogInputMsg", QifActivityLogInputMsg.class);
        configuration.getTypeAliasRegistry().registerAlias("QifActivityLogOutputMsg", QifActivityLogOutputMsg.class);

        configuration.getTypeAliasRegistry().registerAlias("QifAdapter", QifAdapter.class);
        configuration.getTypeAliasRegistry().registerAlias("QifAdapterProperty", QifAdapterProperty.class);

        configuration.getTypeAliasRegistry().registerAlias("QifEvent", QifEvent.class);
        configuration.getTypeAliasRegistry().registerAlias("QifEventProperty", QifEventProperty.class);

        // mappers
        configuration.addMapper(AppSettingSqlmap.class);
        configuration.addMapper(QifCounterSqlmap.class);

        configuration.addMapper(QifEventSqlmap.class);
        configuration.addMapper(QifEventPropertySqlmap.class);
        configuration.addMapper(QifAdapterSqlmap.class);
        configuration.addMapper(QifAdapterPropertySqlmap.class);

        configuration.addMapper(QifActivityLogSqlmap.class);
        configuration.addMapper(QifActivityLogDataSqlmap.class);
        configuration.addMapper(QifActivityLogInputMsgSqlmap.class);
        configuration.addMapper(QifActivityLogOutputMsgSqlmap.class);

        configuration.addMapper(QifEventLogSqlmap.class);
        configuration.addMapper(QifEventLogMsgSqlmap.class);

        return configuration;
    }

}
