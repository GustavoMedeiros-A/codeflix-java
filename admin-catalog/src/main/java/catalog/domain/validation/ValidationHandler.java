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

    default MyError firstError() {
        // return && getErrors().get(0);
        if (getErrors() != null && !(getErrors().isEmpty())) {
            return getErrors().get(0);
        } else {
            return null;
        }

    }

    public interface Validation {
        void validate();
    }

}
