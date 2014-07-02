package id.co.quadras.qif.ui.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.irwin13.winwork.basic.model.entity.app.*;
import com.irwin13.winwork.basic.validator.AbstractValidator;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.ui.validator.app.*;
import id.co.quadras.qif.ui.validator.config.QifAdapterValidator;
import id.co.quadras.qif.ui.validator.config.QifEventValidator;

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
