package id.co.quadras.qif.ui.validator.app;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.entity.app.AppRole;
import com.irwin13.winwork.basic.validator.AbstractValidator;
import com.irwin13.winwork.basic.validator.ValidationStatus;
import com.irwin13.winwork.basic.validator.ValidatorResult;
import id.co.quadras.qif.ui.service.app.AppRoleService;

import java.util.List;

/**
 * @author irwin Timestamp : 23/04/13 16:29
 */
public class AppRoleValidator extends AbstractValidator<AppRole> {

    private final AppRoleService appRoleService;

    @Inject
    public AppRoleValidator(AppRoleService appRoleService) {
        this.appRoleService = appRoleService;
    }

    @Override
    public ValidatorResult<AppRole> onCreate(AppRole model, String errorLang) {
        ValidatorResult validatorResult = ValidatorResult.successResult();

        if (isEmptyString(model.getName())) {
            validatorResult.setValidationStatus(ValidationStatus.ERROR);
            validatorResult.putFieldMessage("name", getDecoratedErrorMessage(errorLang, "error.global.required"));
        } else {
            if (!isEmptyString(model.getName())) {
                AppRole filter = new AppRole();
                filter.setActive(Boolean.TRUE);
                filter.setName(model.getName());

                List<AppRole> list = appRoleService.select(filter, null);
                if (!list.isEmpty()) {
                    validatorResult.setValidationStatus(ValidationStatus.ERROR);
                    validatorResult.putFieldMessage("name", getDecoratedErrorMessage(errorLang, "error.appRole.alreadyExist"));
                }
            }
        }

        validatorResult.setProcessReturnObject(model);
        return validatorResult;
    }

    @Override
    public ValidatorResult<AppRole> onEdit(AppRole model, String errorLang) {
        return ValidatorResult.successResult();
    }

    @Override
    public ValidatorResult<AppRole> onDelete(AppRole model, String errorLang) {
        return ValidatorResult.successResult();
    }
}
