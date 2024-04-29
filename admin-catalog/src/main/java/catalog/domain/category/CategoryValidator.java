package catalog.domain.category;

import catalog.domain.validation.MyError;
import catalog.domain.validation.ValidationHandler;
import catalog.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    public CategoryValidator(final Category aCategory, final ValidationHandler aHandler) {
        super(aHandler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    };

    private void checkNameConstraints() {
        final var name = this.category.getName();
        if (name == null) {
            this.validationHandler().append(new MyError("'name' should not be null"));
            return;
        }
        if (name.isBlank()) {
            this.validationHandler().append(new MyError("'name' should not be empty"));
            return;
        }

        final int lenght = name.trim().length();
        if (lenght > 255 || lenght < 3) {
            this.validationHandler().append(new MyError("'name' must be between 3 and 255 characters"));
            return;

        }
    }

}
