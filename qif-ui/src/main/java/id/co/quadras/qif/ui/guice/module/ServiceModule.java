package id.co.quadras.qif.ui.guice.module;

import com.google.inject.AbstractModule;
import id.co.quadras.qif.ui.service.app.*;
import id.co.quadras.qif.ui.service.app.imp.*;


/**
 * @author irwin Timestamp : 22/09/13 0:02
 */
public class ServiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AppUserService.class).to(AppUserServiceImp.class);
        bind(AppRoleService.class).to(AppRoleServiceImp.class);
        bind(AppPermissionService.class).to(AppPermissionServiceImp.class);
        bind(AppSettingService.class).to(AppSettingServiceImp.class);
        bind(AppOptionService.class).to(AppOptionServiceImp.class);
    }
}
