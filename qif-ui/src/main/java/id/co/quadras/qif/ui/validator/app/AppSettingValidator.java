package id.co.quadras.qif.ui.validator.app;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.entity.app.AppSetting;
import com.irwin13.winwork.basic.validator.AbstractValidator;
import com.irwin13.winwork.basic.validator.ValidationStatus;
import com.irwin13.winwork.basic.validator.ValidatorResult;
import id.co.quadras.qif.ui.service.app.AppSettingService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: NovaSinaga
 * Date: 5/2/13
 * Time: 3:16 PM
 *
 */
public class AppSettingValidator extends AbstractValidator<AppSetting> {

    private final AppSettingService appSettingService;

    @Inject
    public AppSettingValidator(AppSettingService appSettingService) {
        this.appSettingService = appSettingService;
    }

    @Override
    public ValidatorResult<AppSetting> onCreate(AppSetting model, String errorLang) {
        ValidatorResult validatorResult = ValidatorResult.successResult();

        if (isEmptyString(model.getCode())) {
            validatorResult.setValidationStatus(ValidationStatus.ERROR);
            validatorResult.putFieldMessage("code", getDecoratedErrorMessage(errorLang, "error.global.required"));
        }

        if(isEmptyString(model.getStringValue())){
            validatorResult.setValidationStatus(ValidationStatus.ERROR);
            validatorResult.putFieldMessage("stringValue", getDecoratedErrorMessage(errorLang, "error.global.required"));
        }

        if (!isEmptyString(model.getCode())) {
            AppSetting filter = new AppSetting();
            filter.setActive(Boolean.TRUE);
            filter.setCode(model.getCode());

            List<AppSetting> list = appSettingService.select(filter, null);
            if (!list.isEmpty()) {
                validatorResult.setValidationStatus(ValidationStatus.ERROR);
                validatorResult.putFieldMessage("code", getDecoratedErrorMessage(errorLang, "error.appOption.alreadyExist"));
            }
        }

        validatorResult.setProcessReturnObject(model);
        return validatorResult;
    }

    @Override
    public ValidatorResult<AppSetting> onEdit(AppSetting model, String errorLang) {
        return ValidatorResult.successResult();
    }

    @Override
    public ValidatorResult<AppSetting> onDelete(AppSetting model, String errorLang) {
        return ValidatorResult.successResult();
    }
}
