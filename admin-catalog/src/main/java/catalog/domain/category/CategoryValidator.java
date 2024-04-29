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
        if (this.category.getName() == null) {
            this.validationHandler().append(new MyError("'name' should not be null"));
        }
    };
}
