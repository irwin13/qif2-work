package id.co.quadras.qif.engine.guice.module;

import id.co.quadras.qif.engine.dao.AdapterDao;
import id.co.quadras.qif.engine.dao.AdapterPropertyDao;
import id.co.quadras.qif.engine.dao.CounterDao;
import id.co.quadras.qif.engine.dao.EventDao;
import id.co.quadras.qif.engine.dao.EventPropertyDao;
import id.co.quadras.qif.engine.dao.app.AppSettingDao;
import id.co.quadras.qif.engine.dao.app.imp.AppSettingDaoImp;
import id.co.quadras.qif.engine.dao.imp.AdapterDaoImp;
import id.co.quadras.qif.engine.dao.imp.AdapterPropertyDaoImp;
import id.co.quadras.qif.engine.dao.imp.CounterDaoImp;
import id.co.quadras.qif.engine.dao.imp.EventDaoImp;
import id.co.quadras.qif.engine.dao.imp.EventPropertyDaoImp;
import id.co.quadras.qif.engine.dao.log.ActivityLogDao;
import id.co.quadras.qif.engine.dao.log.ActivityLogDataDao;
import id.co.quadras.qif.engine.dao.log.ActivityLogInputMsgDao;
import id.co.quadras.qif.engine.dao.log.ActivityLogOutputMsgDao;
import id.co.quadras.qif.engine.dao.log.EventLogDao;
import id.co.quadras.qif.engine.dao.log.EventLogMsgDao;
import id.co.quadras.qif.engine.dao.log.imp.ActivityLogDaoImp;
import id.co.quadras.qif.engine.dao.log.imp.ActivityLogDataDaoImp;
import id.co.quadras.qif.engine.dao.log.imp.ActivityLogInputMsgDaoImp;
import id.co.quadras.qif.engine.dao.log.imp.ActivityLogOutputMsgDaoImp;
import id.co.quadras.qif.engine.dao.log.imp.EventLogDaoImp;
import id.co.quadras.qif.engine.dao.log.imp.EventLogMsgDaoImp;

import com.google.inject.AbstractModule;

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
