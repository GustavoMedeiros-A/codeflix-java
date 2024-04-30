package catalog.domain.validation.handler;

import java.util.ArrayList;
import java.util.List;

import catalog.domain.exceptions.DomainException;
import catalog.domain.validation.MyError;
import catalog.domain.validation.ValidationHandler;

public class Notification implements ValidationHandler {

    private final List<MyError> errors;

    private Notification(List<MyError> errors) {
        this.errors = errors;
    }

    // Factory method to return a new Notification with a instance of ArrayList
    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final MyError anError) {
        return new Notification(new ArrayList<>()).append(anError);
    }

    @Override
    public Notification append(final MyError anError) {
        this.errors.add(anError);
        return this;
    }

    @Override
    public Notification append(ValidationHandler aHandler) {
        this.errors.addAll(aHandler.getErrors());
        return this;
    }

    @Override
    public ValidationHandler validate(final Validation aValidation) {
        try {
            aValidation.validate();
        } catch (final DomainException ex) {
            this.errors.addAll(ex.getErrors());
        } catch (final Throwable t) {
            this.errors.add(new MyError(t.getMessage()));
        }

        return this;
    }

    @Override
    public List<MyError> getErrors() {
        return this.errors;
    }

}
