package id.co.quadras.qif.ui.validator.config;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.validator.AbstractValidator;
import com.irwin13.winwork.basic.validator.ValidationStatus;
import com.irwin13.winwork.basic.validator.ValidatorResult;
import id.co.quadras.qif.core.model.entity.QifAdapter;
import id.co.quadras.qif.ui.service.config.QifAdapterService;

import java.util.List;

/**
 * @author irwin Timestamp : 02/07/2014 14:05
 */
public class QifAdapterValidator extends AbstractValidator<QifAdapter> {

    private final QifAdapterService adapterService;

    @Inject
    public QifAdapterValidator(QifAdapterService adapterService) {
        this.adapterService = adapterService;
    }

    @Override
    public ValidatorResult<QifAdapter> onCreate(QifAdapter model, String errorLang) {
        ValidatorResult validatorResult = ValidatorResult.successResult();

        if (isEmptyString(model.getName())) {
            validatorResult.setValidationStatus(ValidationStatus.ERROR);
            validatorResult.putFieldMessage("name", getDecoratedErrorMessage(errorLang, "error.global.required"));
        } else {
            if (!isEmptyString(model.getName())) {
                QifAdapter filter = new QifAdapter();
                filter.setActive(Boolean.TRUE);
                filter.setName(model.getName());

                List<QifAdapter> list = adapterService.select(filter, null);
                if (!list.isEmpty()) {
                    validatorResult.setValidationStatus(ValidationStatus.ERROR);
                    validatorResult.putFieldMessage("name", getDecoratedErrorMessage(errorLang, "error.global.alreadyExist"));
                }
            }
        }

        if (isEmptyString(model.getAdapterInterface())) {
            validatorResult.setValidationStatus(ValidationStatus.ERROR);
            validatorResult.putFieldMessage("adapterInterface", getDecoratedErrorMessage(errorLang, "error.global.required"));
        }

        validatorResult.setProcessReturnObject(model);
        return validatorResult;
    }

    @Override
    public ValidatorResult<QifAdapter> onEdit(QifAdapter model, String errorLang) {
        ValidatorResult validatorResult = ValidatorResult.successResult();

        if (isEmptyString(model.getAdapterInterface())) {
            validatorResult.setValidationStatus(ValidationStatus.ERROR);
            validatorResult.putFieldMessage("eventInterface", getDecoratedErrorMessage(errorLang, "error.global.required"));
        }

        validatorResult.setProcessReturnObject(model);
        return validatorResult;
    }

    @Override
    public ValidatorResult<QifAdapter> onDelete(QifAdapter model, String errorLang) {
        return ValidatorResult.successResult();
    }
}
