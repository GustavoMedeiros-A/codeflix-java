package catalog.domain.exceptions;

import java.util.List;
import catalog.domain.validation.MyError;

public class DomainException extends NoStacktraceException {

    private final List<MyError> errors;

    private DomainException(final String aMessage, final List<MyError> anErrors) {
        super(aMessage);
        this.errors = anErrors;
    }

    // Pass ListOf here to avoid use in other classes (A more smart FactoryMethod)
    public static DomainException with(final MyError anErrors) {
        return new DomainException(anErrors.message(), List.of(anErrors));
    }

    public static DomainException with(final List<MyError> anErrors) {
        return new DomainException("", anErrors);
    }

    public List<MyError> getErrors() {
        return errors;
    }

}
