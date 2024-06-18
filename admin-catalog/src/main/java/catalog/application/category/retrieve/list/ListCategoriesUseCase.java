package catalog.application.category.retrieve.list;

import catalog.application.UseCase;
import catalog.domain.category.CategorySearchQuery;
import catalog.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {

}
