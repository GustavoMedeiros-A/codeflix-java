package catalog.application.category.update;

import java.util.Objects;
import java.util.function.Supplier;

import catalog.domain.category.Category;
import catalog.domain.category.CategoryGateway;
import catalog.domain.category.CategoryID;
import catalog.domain.exceptions.DomainException;
import catalog.domain.validation.MyError;
import catalog.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand aCommand) {
        final var anId = CategoryID.from(aCommand.id());
        final var name = aCommand.name();
        final var description = aCommand.description();
        final var isActive = aCommand.isActive();

        final var aCategory = this.categoryGateway.findById(anId).orElseThrow(notFound(anId));
        final var notification = Notification.create();

        aCategory.update(name, description, isActive).validate(notification);
        return notification.hasError() ? API.Left(notification) : update(aCategory);
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category aCategory) {
        return API.Try(() -> this.categoryGateway.update(aCategory)).toEither().bimap(Notification::create,
                UpdateCategoryOutput::from);
    }

    private Supplier<DomainException> notFound(CategoryID anId) {
        return () -> DomainException.with(new MyError("Not found category with ID %s".formatted(anId.getValue())));
    }
}
