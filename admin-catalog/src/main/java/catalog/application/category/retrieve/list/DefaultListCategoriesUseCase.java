package catalog.application.category.retrieve.list;

import java.util.Objects;

import catalog.domain.category.CategoryGateway;
import catalog.domain.category.CategorySearchQuery;
import catalog.domain.pagination.Pagination;

public class DefaultListCategoriesUseCase extends ListCategoriesUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultListCategoriesUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(CategorySearchQuery aQuery) {
        return this.categoryGateway.findAll(aQuery).map(CategoryListOutput::from);
    }
}
