package id.co.quadras.qif.engine.guice.module;

import com.google.inject.AbstractModule;
import id.co.quadras.qif.engine.dao.*;
import id.co.quadras.qif.engine.dao.app.AppSettingDao;
import id.co.quadras.qif.engine.dao.app.imp.AppSettingDaoImp;
import id.co.quadras.qif.engine.dao.imp.*;
import id.co.quadras.qif.engine.dao.log.*;
import id.co.quadras.qif.engine.dao.log.imp.*;

/**
 * @author irwin Timestamp : 12/05/2014 17:16
 */
public class DaoModule extends AbstractModule {

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
