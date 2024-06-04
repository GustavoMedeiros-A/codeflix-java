package catalog.application.category.update;

import catalog.domain.category.Category;
import catalog.domain.category.CategoryID;

public record UpdateCategoryOutput(CategoryID id) {
    public static UpdateCategoryOutput from(final Category aCategory) {
        return new UpdateCategoryOutput(aCategory.getId());
    }
}
