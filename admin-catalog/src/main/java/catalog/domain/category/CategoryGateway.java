package catalog.domain.category;

import java.util.Optional;

import catalog.domain.pagination.Pagination;

public interface CategoryGateway {

    Category create(Category aCategory);

    void deleteById(CategoryID anID);

    Optional<Category> getById(CategoryID anID);

    Category update(Category aCategory);

    Pagination<Category> findAll(CategorySearchQuery aQuery);
}
