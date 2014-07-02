package id.co.quadras.qif.ui.validator.config;

import com.google.inject.Inject;
import com.irwin13.winwork.basic.validator.AbstractValidator;
import com.irwin13.winwork.basic.validator.ValidationStatus;
import com.irwin13.winwork.basic.validator.ValidatorResult;
import id.co.quadras.qif.core.model.entity.QifEvent;
import id.co.quadras.qif.ui.service.config.QifEventService;

import java.util.List;

/**
 * @author irwin Timestamp : 02/07/2014 14:05
 */
public class QifEventValidator extends AbstractValidator<QifEvent> {

    private final QifEventService eventService;

    @Inject
    public QifEventValidator(QifEventService eventService) {
        this.eventService = eventService;
    }

    @Override
    public ValidatorResult<QifEvent> onCreate(QifEvent model, String errorLang) {
        ValidatorResult validatorResult = ValidatorResult.successResult();

        if (isEmptyString(model.getName())) {
            validatorResult.setValidationStatus(ValidationStatus.ERROR);
            validatorResult.putFieldMessage("name", getDecoratedErrorMessage(errorLang, "error.global.required"));
        } else {
            if (!isEmptyString(model.getName())) {
                QifEvent filter = new QifEvent();
                filter.setActive(Boolean.TRUE);
                filter.setName(model.getName());

                List<QifEvent> list = eventService.select(filter, null);
                if (!list.isEmpty()) {
                    validatorResult.setValidationStatus(ValidationStatus.ERROR);
                    validatorResult.putFieldMessage("name", getDecoratedErrorMessage(errorLang, "error.global.alreadyExist"));
                }
            }
        }

        if (isEmptyString(model.getEventType())) {
            validatorResult.setValidationStatus(ValidationStatus.ERROR);
            validatorResult.putFieldMessage("eventType", getDecoratedErrorMessage(errorLang, "error.global.required"));
        }

        if (isEmptyString(model.getEventInterface())) {
            validatorResult.setValidationStatus(ValidationStatus.ERROR);
            validatorResult.putFieldMessage("eventInterface", getDecoratedErrorMessage(errorLang, "error.global.required"));
        }

        if (isEmptyString(model.getQifProcess())) {
            validatorResult.setValidationStatus(ValidationStatus.ERROR);
            validatorResult.putFieldMessage("qifProcess", getDecoratedErrorMessage(errorLang, "error.global.required"));
        }

        validatorResult.setProcessReturnObject(model);
        return validatorResult;
    }

    @Override
    public ValidatorResult<QifEvent> onEdit(QifEvent model, String errorLang) {
        ValidatorResult validatorResult = ValidatorResult.successResult();

        if (isEmptyString(model.getEventType())) {
            validatorResult.setValidationStatus(ValidationStatus.ERROR);
            validatorResult.putFieldMessage("eventType", getDecoratedErrorMessage(errorLang, "error.global.required"));
        }

        if (isEmptyString(model.getEventInterface())) {
            validatorResult.setValidationStatus(ValidationStatus.ERROR);
            validatorResult.putFieldMessage("eventInterface", getDecoratedErrorMessage(errorLang, "error.global.required"));
        }

        if (isEmptyString(model.getQifProcess())) {
            validatorResult.setValidationStatus(ValidationStatus.ERROR);
            validatorResult.putFieldMessage("qifProcess", getDecoratedErrorMessage(errorLang, "error.global.required"));
        }

        validatorResult.setProcessReturnObject(model);
        return validatorResult;
    }

    @Override
    public ValidatorResult<QifEvent> onDelete(QifEvent qifEvent, String errorLang) {
        return ValidatorResult.successResult();
    }
}
