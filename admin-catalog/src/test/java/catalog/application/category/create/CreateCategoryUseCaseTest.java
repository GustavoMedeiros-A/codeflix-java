package catalog.application.category.create;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.AdditionalAnswers;

import catalog.domain.category.CategoryGateway;

public class CreateCategoryUseCaseTest {

    // 1. Test of the HAPPY path
    // 2. Test with invalid properties
    // 3. Test create inactive category
    // 4. Test with a generic error from the GATEWAY (Interface)

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryID() {

        final var expectedName = "Filmes";
        final var expectedDescription = "The category";
        final var expectedIsActive = true;

        // Factory method
        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);
        // When call create method from Gateway return the first params (That need to be
        // the created Category)
        Mockito.when(categoryGateway.create(Mockito.any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var useCase = new CreateCategoryUseCase();
        // Pass the execute
        // UseCase implement the Command
        final var actualOutput = useCase.execute(aCommand);

        assertNotNull(actualOutput);
        assertNotNull(categoryGateway.getId());

        // Verify with the create was called one time
        // And verify expected name with the current category Name
        // Lambda Function
        Mockito.verify(categoryGateway, Mockito.times(1)).create(Mockito.argThat(aCategory -> {
            return Objects.equals(expectedName, aCategory.getName())
                    && Objects.equals(expectedDescription, aCategory.getDescription())
                    && Objects.equals(expectedIsActive, aCategory.isActive())
                    && Objects.nonNull(aCategory.getId())
                    && Objects.nonNull(aCategory.getCreatedAt())
                    && Objects.nonNull(aCategory.getUpdatedAt())
                    && Objects.isNull(aCategory.getDeletedAt());
        }));
    }
}
