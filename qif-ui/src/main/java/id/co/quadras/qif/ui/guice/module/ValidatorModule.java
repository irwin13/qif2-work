package id.co.quadras.qif.ui.guice.module;

import id.co.quadras.qif.model.entity.QifAdapter;
import id.co.quadras.qif.model.entity.QifEvent;
import id.co.quadras.qif.ui.validator.app.AppOptionValidator;
import id.co.quadras.qif.ui.validator.app.AppPermissionValidator;
import id.co.quadras.qif.ui.validator.app.AppRoleValidator;
import id.co.quadras.qif.ui.validator.app.AppSettingValidator;
import id.co.quadras.qif.ui.validator.app.AppUserValidator;
import id.co.quadras.qif.ui.validator.config.QifAdapterValidator;
import id.co.quadras.qif.ui.validator.config.QifEventValidator;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.irwin13.winwork.basic.model.entity.app.AppOption;
import com.irwin13.winwork.basic.model.entity.app.AppPermission;
import com.irwin13.winwork.basic.model.entity.app.AppRole;
import com.irwin13.winwork.basic.model.entity.app.AppSetting;
import com.irwin13.winwork.basic.model.entity.app.AppUser;
import com.irwin13.winwork.basic.validator.AbstractValidator;

/**
 * @author irwin Timestamp : 22/09/13 0:37
 */
public class ValidatorModule extends AbstractModule {

    @Override
    protected void configure() {
        // APP
        bind(AbstractValidator.class).annotatedWith(Names.named(AppOption.MODEL_NAME)).to(AppOptionValidator.class);
        bind(AbstractValidator.class).annotatedWith(Names.named(AppSetting.MODEL_NAME)).to(AppSettingValidator.class);
        bind(AbstractValidator.class).annotatedWith(Names.named(AppUser.MODEL_NAME)).to(AppUserValidator.class);
        bind(AbstractValidator.class).annotatedWith(Names.named(AppPermission.MODEL_NAME)).to(AppPermissionValidator.class);
        bind(AbstractValidator.class).annotatedWith(Names.named(AppRole.MODEL_NAME)).to(AppRoleValidator.class);

        // CONFIG
        bind(AbstractValidator.class).annotatedWith(Names.named(QifEvent.MODEL_NAME)).to(QifEventValidator.class);
        bind(AbstractValidator.class).annotatedWith(Names.named(QifAdapter.MODEL_NAME)).to(QifAdapterValidator.class);
    }
}
