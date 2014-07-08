package id.co.quadras.qif.ui.guice.module;

import com.google.inject.AbstractModule;
import id.co.quadras.qif.ui.service.app.*;
import id.co.quadras.qif.ui.service.app.imp.*;
import id.co.quadras.qif.ui.service.config.QifAdapterService;
import id.co.quadras.qif.ui.service.config.QifEventService;
import id.co.quadras.qif.ui.service.config.imp.QifAdapterServiceImp;
import id.co.quadras.qif.ui.service.config.imp.QifEventServiceImp;
import id.co.quadras.qif.ui.service.monitoring.EventInstanceService;
import id.co.quadras.qif.ui.service.monitoring.imp.EventInstanceServiceImp;


/**
 * @author irwin Timestamp : 22/09/13 0:02
 */
public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        // APP
        bind(AppUserService.class).to(AppUserServiceImp.class);
        bind(AppRoleService.class).to(AppRoleServiceImp.class);
        bind(AppPermissionService.class).to(AppPermissionServiceImp.class);
        bind(AppSettingService.class).to(AppSettingServiceImp.class);
        bind(AppOptionService.class).to(AppOptionServiceImp.class);

        // CONFIG
        bind(QifEventService.class).to(QifEventServiceImp.class);
        bind(QifAdapterService.class).to(QifAdapterServiceImp.class);

        // MONITORING
        bind(EventInstanceService.class).to(EventInstanceServiceImp.class);
    }
}
