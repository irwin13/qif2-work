package id.co.quadras.qif.dev.guice;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;

/**
 * @author irwin Timestamp : 06/06/2014 17:53
 */
public class MyBatisSessionFactoryProvider implements Provider<SqlSessionFactory> {

    private final String qifConfigFile;

    @Inject
    public MyBatisSessionFactoryProvider(@Named("qifConfigFile") String qifConfigFile) {
        this.qifConfigFile = qifConfigFile;
    }

    @Override
    public SqlSessionFactory get() {
        DataSource dataSource = null;
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        return null;
    }
}
