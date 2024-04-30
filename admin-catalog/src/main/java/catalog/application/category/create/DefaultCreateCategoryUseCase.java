package catalog.application.category.create;

import java.util.Objects;

import catalog.domain.category.Category;
import catalog.domain.category.CategoryGateway;
import catalog.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(CreateCategoryCommand aCommand) {
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();

        final var notification = Notification.create();
        final var aCategory = Category.newCategory(aName, aDescription, isActive);

        aCategory.validate(notification);

        // Pass the aCategory to the gateway to create
        // And pass CreateCategoryOutput to send the data to the FRONT-END or whatever
        // return CreateCategoryOutput.from(this.categoryGateway.create(aCategory));
        return notification.hasError() ? API.Left(notification) : create(aCategory);
    }

    private Either<Notification, CreateCategoryOutput> create(Category aCategory) {
        return API.Right(CreateCategoryOutput.from(this.categoryGateway.create(aCategory)));
    }
}
