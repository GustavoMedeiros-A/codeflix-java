package catalog.domain.exceptions;

import java.util.List;
import catalog.domain.validation.MyError;

public class DomainException extends RuntimeException {

    private final List<MyError> errors;

    private DomainException(final List<MyError> anErrors) {
        super("", null, true, false);
        this.errors = anErrors;
    }

    // public static DomainException with(final Error anErrors) {
    // return new DomainException(anErrors);
    // }

    public static DomainException with(final List<MyError> anErrors) {
        return new DomainException(anErrors);
    }

    public List<MyError> getErrors() {
        return errors;
    }

}
