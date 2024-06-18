package catalog.application.category.retrieve.get;

import java.util.Objects;
import java.util.function.Supplier;

import catalog.domain.category.CategoryGateway;
import catalog.domain.category.CategoryID;
import catalog.domain.exceptions.DomainException;
import catalog.domain.validation.MyError;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryByIdUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CategoryOutput execute(String aIn) {
        final var categoryId = CategoryID.from(aIn);
        return this.categoryGateway.findById(categoryId).map(CategoryOutput::from).orElseThrow(notFound(categoryId));
    }

    private Supplier<DomainException> notFound(CategoryID anId) {
        return () -> DomainException.with(new MyError("Not found category with ID %s".formatted(anId.getValue())));
    }

}