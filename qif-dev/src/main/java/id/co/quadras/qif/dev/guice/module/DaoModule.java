package id.co.quadras.qif.dev.guice.module;

import com.google.inject.AbstractModule;
import id.co.quadras.qif.dev.dao.AdapterDao;
import id.co.quadras.qif.dev.dao.AdapterPropertyDao;
import id.co.quadras.qif.dev.dao.EventDao;
import id.co.quadras.qif.dev.dao.EventPropertyDao;
import id.co.quadras.qif.dev.dao.imp.AdapterDaoImp;
import id.co.quadras.qif.dev.dao.imp.AdapterPropertyDaoImp;
import id.co.quadras.qif.dev.dao.imp.EventDaoImp;
import id.co.quadras.qif.dev.dao.imp.EventPropertyDaoImp;
import id.co.quadras.qif.dev.dao.log.*;
import id.co.quadras.qif.dev.dao.log.imp.*;

/**
 * @author irwin Timestamp : 12/05/2014 17:16
 */
public class DaoModule extends AbstractModule {

    @Override
    protected void configure() {
        // LOG
        bind(ActivityLogDao.class).to(ActivityLogDaoImp.class);
        bind(ActivityLogDataDao.class).to(ActivityLogDataDaoImp.class);
        bind(ActivityLogInputMessageDao.class).to(ActivityLogInputMessageDaoImp.class);
        bind(ActivityLogOutputMessageDao.class).to(ActivityLogOutputMessageDaoImp.class);

        bind(EventLogDao.class).to(EventLogDaoImp.class);
        bind(EventLogMessageDao.class).to(EventLogMessageDaoImp.class);

        // CONFIGURATIOn
        bind(EventDao.class).to(EventDaoImp.class);
        bind(EventPropertyDao.class).to(EventPropertyDaoImp.class);

        bind(AdapterDao.class).to(AdapterDaoImp.class);
        bind(AdapterPropertyDao.class).to(AdapterPropertyDaoImp.class);

    }
}
