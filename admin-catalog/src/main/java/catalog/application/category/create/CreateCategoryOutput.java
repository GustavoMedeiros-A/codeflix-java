package catalog.application.category.create;

import catalog.domain.category.Category;
import catalog.domain.category.CategoryID;

public record CreateCategoryOutput(
        CategoryID id) {
    public static CreateCategoryOutput from(final Category aCategory) {
        return new CreateCategoryOutput(aCategory.getId());
    }
}
