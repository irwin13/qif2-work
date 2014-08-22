package id.co.quadras.qif.engine.guice.module;

import com.google.common.base.Strings;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.irwin13.winwork.basic.model.SearchParameter;
import com.irwin13.winwork.basic.model.entity.app.AppSetting;
import id.co.quadras.qif.engine.QifException;
import id.co.quadras.qif.engine.config.QifConfig;
import id.co.quadras.qif.engine.core.QifConstants;
import id.co.quadras.qif.engine.dao.*;
import id.co.quadras.qif.engine.dao.app.AppSettingDao;
import id.co.quadras.qif.engine.dao.app.imp.AppSettingDaoImp;
import id.co.quadras.qif.engine.dao.imp.*;
import id.co.quadras.qif.engine.dao.log.*;
import id.co.quadras.qif.engine.dao.log.imp.*;
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
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

/**
 * @author irwin Timestamp : 12/05/2014 17:16
 */
public class DaoModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(DaoModule.class);

    @Override
    protected void configure() {

        // LOG
        bind(ActivityLogDao.class).to(ActivityLogDaoImp.class);
        bind(ActivityLogDataDao.class).to(ActivityLogDataDaoImp.class);
        bind(ActivityLogInputMsgDao.class).to(ActivityLogInputMsgDaoImp.class);
        bind(ActivityLogOutputMsgDao.class).to(ActivityLogOutputMsgDaoImp.class);

        bind(EventLogDao.class).to(EventLogDaoImp.class);
        bind(EventLogMsgDao.class).to(EventLogMsgDaoImp.class);

        // CONFIGURATION
        bind(EventDao.class).to(EventDaoImp.class);
        bind(EventPropertyDao.class).to(EventPropertyDaoImp.class);

        bind(AdapterDao.class).to(AdapterDaoImp.class);
        bind(AdapterPropertyDao.class).to(AdapterPropertyDaoImp.class);

        // Counter
        bind(CounterDao.class).to(CounterDaoImp.class);

        // APP
        bind(AppSettingDao.class).to(AppSettingDaoImp.class);
    }

}
