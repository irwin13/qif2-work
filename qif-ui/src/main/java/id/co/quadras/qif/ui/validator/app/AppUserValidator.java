package id.co.quadras.qif.ui.validator.app;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.model.entity.app.AppUser;
import com.irwin13.winwork.basic.validator.AbstractValidator;
import com.irwin13.winwork.basic.validator.ValidationStatus;
import com.irwin13.winwork.basic.validator.ValidatorResult;
import id.co.quadras.qif.ui.service.app.AppUserService;

import java.util.List;

/**
 * @author irwin Timestamp : 23/04/13 16:29
 */
public class AppUserValidator extends AbstractValidator<AppUser> {

    private final AppUserService appUserService;

    @Inject
    public AppUserValidator(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @Override
    public ValidatorResult<AppUser> onCreate(AppUser model, String errorLang) {
        ValidatorResult validatorResult = ValidatorResult.successResult();

        if (isEmptyString(model.getUsername())) {
            validatorResult.setValidationStatus(ValidationStatus.ERROR);
            validatorResult.putFieldMessage("username", getDecoratedErrorMessage(errorLang, "error.global.required"));
        } else {
            if (!isEmptyString(model.getUsername())) {
                AppUser filter = new AppUser();
                filter.setActive(Boolean.TRUE);
                filter.setUsername(model.getUsername());

                List<AppUser> list = appUserService.select(filter, null);
                if (!list.isEmpty()) {
                    validatorResult.setValidationStatus(ValidationStatus.ERROR);
                    validatorResult.putFieldMessage("username", getDecoratedErrorMessage(errorLang, "error.appUser.alreadyExist"));
                }
            }

        }

        validatorResult.setProcessReturnObject(model);
        return validatorResult;
    }

    @Override
    public ValidatorResult<AppUser> onEdit(AppUser model, String errorLang) {
        return ValidatorResult.successResult();
    }

    @Override
    public ValidatorResult<AppUser> onDelete(AppUser model, String errorLang) {
        return ValidatorResult.successResult();
    }
}
