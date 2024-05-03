package catalog.application.category.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import catalog.domain.category.CategoryGateway;

import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    // 1. Test of the HAPPY path - Done
    // 2. Test with invalid properties - Done
    // 3. Test create inactive category - Done
    // 4. Test with a generic error from the GATEWAY (Interface) - Done

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
        Mockito.when(categoryGateway.create(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        // Use Implementation
        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);
        // Pass the execute
        // UseCase implement the Command
        final var actualOutput = useCase.execute(aCommand).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        // Verify with the create was called one time
        // And verify expected name with the current category Name
        // Lambda Function
        Mockito.verify(categoryGateway, times(1)).create(argThat(aCategory -> {
            return Objects.equals(expectedName, aCategory.getName())
                    && Objects.equals(expectedDescription, aCategory.getDescription())
                    && Objects.equals(expectedIsActive, aCategory.isActive())
                    && Objects.nonNull(aCategory.getId())
                    && Objects.nonNull(aCategory.getCreatedAt())
                    && Objects.nonNull(aCategory.getUpdatedAt())
                    && Objects.isNull(aCategory.getDeletedAt());
        }));
    }

    @Test
    public void givenAInvalidName_whenCallsCreateCategory_thenShouldReturnDomainException() {
        final String expectedName = null;
        final var expectedDescription = "The category";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);

        // Mockito Do no get here, so we don't have to run this teste
        // when(categoryGateway.create(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        // The test verify that need to return a DomainException when the
        // useCase.execute is called
        final var notification = this.useCase.execute(aCommand).getLeft();
        // final var actualException = assertThrows(DomainException.class, () ->
        // notification);

        assertEquals(expectedErrorMessage, notification.firstError().message());
        assertEquals(expectedErrorCount, notification.getErrors().size());

        Mockito.verify(categoryGateway, times(0)).create(any());
    }

    @Test
    public void givenAInvalidCommandWithInactiveCategory_whenCallsCreateCategory_thenShouldReturnInactiveCategoryId() {
        final var expectedName = "Filmes";
        final var expectedDescription = "The category";
        final var expectedIsActive = false;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);
        Mockito.when(categoryGateway.create(any())).thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);
        final var actualOutput = useCase.execute(aCommand).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).create(argThat(aCategory -> {
            return Objects.equals(expectedName, aCategory.getName())
                    && Objects.equals(expectedDescription, aCategory.getDescription())
                    && Objects.equals(expectedIsActive, aCategory.isActive())
                    && Objects.nonNull(aCategory.getId())
                    && Objects.nonNull(aCategory.getCreatedAt())
                    && Objects.nonNull(aCategory.getUpdatedAt())
                    && Objects.nonNull(aCategory.getDeletedAt());
        }));
    }

    @Test
    public void givenAInvalidCommand_whenGatewayThrowsRandomException_thenShouldReturnAException() {
        final var expectedName = "Filmes";
        final var expectedDescription = "The category";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway Error";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        // final CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);

        Mockito.when(this.categoryGateway.create(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = this.useCase.execute(aCommand).getLeft();
        // final var actualException = assertThrows(IllegalStateException.class, () ->
        // this.useCase.execute(aCommand));

        assertEquals(expectedErrorMessage, notification.firstError().message());
        assertEquals(expectedErrorCount, notification.getErrors().size());

        verify(this.categoryGateway, times(1)).create(any());
    }
}
