package catalog.domain.validation.handler;

import java.util.List;

import catalog.domain.exceptions.DomainException;
import catalog.domain.validation.Error;
import catalog.domain.validation.ValidationHandler;

public class ThrowsValidationHandler implements ValidationHandler {

    @Override
    public ValidationHandler append(final Error anError) {
        throw DomainException.with(List.of(anError));
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
            throw DomainException.with(List.of(ex.getMessage()));
        }

        return this;
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }

}
