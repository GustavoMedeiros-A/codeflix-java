package catalog.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(MyError anError);

    ValidationHandler append(ValidationHandler aHandler);

    ValidationHandler validate(Validation aValidation);

    List<MyError> getErrors();

    default boolean hasError() {
        return getErrors() != null && !(getErrors().isEmpty());
    }

    public interface Validation {
        void validate();
    }

}
