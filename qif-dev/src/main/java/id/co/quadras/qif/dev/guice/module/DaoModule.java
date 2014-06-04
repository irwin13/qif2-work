package id.co.quadras.qif.dev.guice.module;

import com.google.inject.AbstractModule;
import com.irwin13.winwork.mybatis.guice.provider.SqlSessionFactoryProvider;
import id.co.quadras.qif.dev.dao.*;
import id.co.quadras.qif.dev.dao.imp.*;
import id.co.quadras.qif.dev.dao.log.*;
import id.co.quadras.qif.dev.dao.log.imp.*;
import org.apache.ibatis.session.SqlSessionFactory;

/**
 * @author irwin Timestamp : 12/05/2014 17:16
 */
public class DaoModule extends AbstractModule {

    @Override
    protected void configure() {

        // MyBatis
        bind(SqlSessionFactory.class).toProvider(SqlSessionFactoryProvider.class);

        // LOG
        bind(ActivityLogDao.class).to(ActivityLogDaoImp.class);
        bind(ActivityLogDataDao.class).to(ActivityLogDataDaoImp.class);
        bind(ActivityLogInputMessageDao.class).to(ActivityLogInputMessageDaoImp.class);
        bind(ActivityLogOutputMessageDao.class).to(ActivityLogOutputMessageDaoImp.class);

        bind(EventLogDao.class).to(EventLogDaoImp.class);
        bind(EventLogMessageDao.class).to(EventLogMessageDaoImp.class);

        // CONFIGURATION
        bind(EventDao.class).to(EventDaoImp.class);
        bind(EventPropertyDao.class).to(EventPropertyDaoImp.class);

        bind(AdapterDao.class).to(AdapterDaoImp.class);
        bind(AdapterPropertyDao.class).to(AdapterPropertyDaoImp.class);

        bind(CounterDao.class).to(CounterDaoImp.class);
    }
}
