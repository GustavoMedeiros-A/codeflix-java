package catalog.application.category.retrieve.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.junit.jupiter.MockitoExtension;

import catalog.domain.category.Category;
import catalog.domain.category.CategoryGateway;
import catalog.domain.category.CategoryID;
import catalog.domain.exceptions.DomainException;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCaseTest {

    @InjectMocks
    private DefaultGetCategoryByIdUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void shouldReturnCategoryWhenCallsGetCategoryById() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var expectedId = aCategory.getId();

        Mockito.when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));

        final var actualCategory = useCase.execute(expectedId.getValue());

        assertEquals(CategoryOutput.from(aCategory), actualCategory);
        assertEquals(expectedName, actualCategory.name());
        assertEquals(expectedId, actualCategory.id());
        assertEquals(expectedDescription, actualCategory.description());
        assertEquals(aCategory.getCreatedAt(), actualCategory.createdAt());
        assertEquals(aCategory.getDeletedAt(), actualCategory.deletedAt());

    }

    @Test
    public void shouldThrowNotFoundExceptionWhenCallsGetCategoryByIdWithInvalidId() {
        final var expectedErrorMessage = "Not found category with ID 123";
        final var expectedId = CategoryID.from("123");

        Mockito.when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(DomainException.class,
                () -> useCase.execute(expectedId.getValue()));

        assertEquals(expectedErrorMessage, actualException.getMessage());

    }

    @Test
    public void shouldThrowsReturnExceptionWhenGatewayThrowsException_withInvalidId() {
        final var expectedErrorMessage = "Not found category with ID 123";
        final var expectedId = CategoryID.from("123");

        Mockito.when(categoryGateway.findById(eq(expectedId)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = Assertions.assertThrows(IllegalStateException.class,
                () -> useCase.execute(expectedId.getValue()));

        assertEquals(expectedErrorMessage, actualException.getMessage());

    }
}
