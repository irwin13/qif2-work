package id.co.quadras.qif.ui.guice.module;

import com.google.inject.AbstractModule;
import id.co.quadras.qif.ui.dao.app.*;
import id.co.quadras.qif.ui.dao.app.imp.*;
import id.co.quadras.qif.ui.dao.config.QifAdapterDao;
import id.co.quadras.qif.ui.dao.config.QifEventDao;
import id.co.quadras.qif.ui.dao.config.imp.QifAdapterDaoImp;
import id.co.quadras.qif.ui.dao.config.imp.QifEventDaoImp;

/**
 * @author irwin Timestamp : 22/09/13 0:02
 */
public class DaoModule extends AbstractModule {

    @Override
    protected void configure() {
        // APP
        bind(AppUserDao.class).to(AppUserDaoImp.class);
        bind(AppRoleDao.class).to(AppRoleDaoImp.class);
        bind(AppPermissionDao.class).to(AppPermissionDaoImp.class);
        bind(AppSettingDao.class).to(AppSettingDaoImp.class);
        bind(AppOptionDao.class).to(AppOptionDaoImp.class);

        // CONFIG
        bind(QifEventDao.class).to(QifEventDaoImp.class);
        bind(QifAdapterDao.class).to(QifAdapterDaoImp.class);
    }
}
