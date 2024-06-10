package catalog.application.category.delete;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;

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

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void shouldDeleteACategory_WhenPassAValidID() {

        final var aCategory = Category.newCategory("filmes", "description", true);
        final var expectedId = aCategory.getId();

        // Do nothing when pass deleteById
        Mockito.doNothing().when(categoryGateway).deleteById(eq(expectedId));

        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void shouldBeOk_WhenPassAValidId() {
        // final var aCategory = Category.newCategory("filmes", "description", true);
        final var expectedId = CategoryID.from("123");

        // Do nothing when pass deleteById
        Mockito.doNothing().when(categoryGateway).deleteById(eq(expectedId));

        assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    public void shouldGatewayThrowsException_WhenPassValidId() {
        final var aCategory = Category.newCategory("filmes", "description", true);
        final var expectedId = aCategory.getId();

        // Do nothing when pass deleteById
        Mockito.doThrow(new IllegalStateException("Gateway error")).when(categoryGateway).deleteById(eq(expectedId));

        assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }
}
