package catalog.application.category.create;

import catalog.application.UseCase;
import catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

// Não trabalhe pra uma implementação, trabalhe pra uma ABSTRAÇÂO
// A função createCategory pode mudar ao longo do tempo

// FIXME: Add Either here, so remember that and do not need to fix nothing

public abstract class CreateCategoryUseCase
        extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {

}
