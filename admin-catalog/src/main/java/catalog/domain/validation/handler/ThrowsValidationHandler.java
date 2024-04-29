package catalog.domain.validation.handler;

import java.util.List;

import catalog.domain.exceptions.DomainException;
import catalog.domain.validation.MyError;
import catalog.domain.validation.ValidationHandler;

public class ThrowsValidationHandler implements ValidationHandler {

    @Override
    public ValidationHandler append(final MyError anError) {
        throw DomainException.with(anError);
    }

    @Override
    public ValidationHandler append(final ValidationHandler anHandler) {

        throw DomainException.with(anHandler.getErrors());
    }

    @Override
    public ValidationHandler validate(Validation aValidation) {
        try {
            aValidation.validate();
        } catch (final Exception ex) {
            throw DomainException.with(new MyError(ex.getMessage()));
        }

        return this;
    }

    @Override
    public List<MyError> getErrors() {
        return List.of();
    }

}
