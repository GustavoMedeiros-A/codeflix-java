package catalog.application.category.update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;

import catalog.application.category.create.CreateCategoryCommand;
import catalog.domain.category.Category;
import catalog.domain.category.CategoryGateway;
import catalog.domain.category.CategoryID;
import catalog.domain.exceptions.DomainException;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

        // 1. Caminho Feliz - Done
        // 2. Passando uma propriedade invalida - Done
        // 3. Teste atualizando uma categoria inawtiva - Done
        // 4. Teste simulando um erro generico vindo do Gateway - Done
        // 5. Atualizar categia passando ID invalido

        @InjectMocks
        private DefaultUpdateCategoryUseCase useCase;

        @Mock
        CategoryGateway categoryGateway;

        @Test
        public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryID() {
                final var aCategory = Category.newCategory("Film", null, true);

                final var expectedName = "Filmes";
                final var expectedDescription = "A categoria mais assistida";
                final var expectedIsActive = true;
                final var expectedId = aCategory.getId();

                final var aCommand = UpdateCategoryCommand.with(
                                expectedId.getValue(),
                                expectedName,
                                expectedDescription,
                                expectedIsActive);

                when(categoryGateway.findById(eq(expectedId)))
                                .thenReturn(Optional.of(Category.myClone(aCategory)));

                when(categoryGateway.update(any()))
                                .thenAnswer(returnsFirstArg());

                final var actualOutput = useCase.execute(aCommand).get();

                Assertions.assertNotNull(actualOutput);
                Assertions.assertNotNull(actualOutput.id());

                Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));

                Mockito.verify(categoryGateway, times(1)).update(argThat(
                                aUpdatedCategory -> Objects.equals(expectedName, aUpdatedCategory.getName())
                                                && Objects.equals(expectedDescription,
                                                                aUpdatedCategory.getDescription())
                                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                                && Objects.equals(aCategory.getCreatedAt(),
                                                                aUpdatedCategory.getCreatedAt())
                                                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                                && Objects.isNull(aUpdatedCategory.getDeletedAt())));
        }

        @Test
        public void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() {
                final var aCategory = Category.newCategory("Film", null, true);

                final String expectedName = null;
                final var expectedDescription = "The category";
                final var expectedIsActive = true;
                final var expectedErrorMessage = "'name' should not be null";
                final var expectedErrorCount = 1;

                final var expectedId = aCategory.getId();

                final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName,
                                expectedDescription, expectedIsActive);

                when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(Category.myClone(aCategory)));

                final var notification = useCase.execute(aCommand).getLeft();

                assertEquals(expectedErrorMessage, notification.firstError().message());
                assertEquals(expectedErrorCount, notification.getErrors().size());

                Mockito.verify(categoryGateway, times(0)).update(any());
        }

        @Test
        public void givenAValidInactivateCommand_whenCallsUpdateCategory_shouldReturnInactiveCategoryId() {
                final var aCategory = Category.newCategory("film", null, true);

                final String expectedName = "Filmes";
                final var expectedDescription = "The category";
                final var expectedIsActive = false;
                final var expectedId = aCategory.getId();

                final var aCommand = UpdateCategoryCommand.with(
                                expectedId.getValue(),
                                expectedName,
                                expectedDescription,
                                expectedIsActive);

                when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(Category.myClone(aCategory)));
                when(categoryGateway.update(any()))
                                .thenAnswer(returnsFirstArg());

                assertTrue(aCategory.isActive());
                assertNull(aCategory.getDeletedAt());

                final var actualOutput = useCase.execute(aCommand).get();

                assertNotNull(actualOutput);
                assertNotNull(actualOutput.id());

                Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));

                Mockito.verify(categoryGateway, times(1)).update(argThat(
                                aUpdatedCategory -> Objects.equals(expectedName, aUpdatedCategory.getName())
                                                && Objects.equals(expectedDescription,
                                                                aUpdatedCategory.getDescription())
                                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                                && Objects.equals(aCategory.getCreatedAt(),
                                                                aUpdatedCategory.getCreatedAt())
                                                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                                && Objects.nonNull(aUpdatedCategory.getDeletedAt())));

        }

        @Test
        public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
                final var aCategory = Category.newCategory("Film", null, true);

                final var expectedName = "Filmes";
                final var expectedDescription = "A categoria mais assistida";
                final var expectedIsActive = true;
                final var expectedId = aCategory.getId();
                final var expectedErrorCount = 1;
                final var expectedErrorMessage = "Gateway error";

                final var aCommand = UpdateCategoryCommand.with(
                                expectedId.getValue(),
                                expectedName,
                                expectedDescription,
                                expectedIsActive);

                when(categoryGateway.findById(eq(expectedId)))
                                .thenReturn(Optional.of(Category.myClone(aCategory)));

                when(categoryGateway.update(any()))
                                .thenThrow(new IllegalStateException(expectedErrorMessage));

                final var notification = useCase.execute(aCommand).getLeft();

                Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
                Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

                Mockito.verify(categoryGateway, times(1)).update(argThat(
                                aUpdatedCategory -> Objects.equals(expectedName, aUpdatedCategory.getName())
                                                && Objects.equals(expectedDescription,
                                                                aUpdatedCategory.getDescription())
                                                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                                                && Objects.equals(expectedId, aUpdatedCategory.getId())
                                                && Objects.equals(aCategory.getCreatedAt(),
                                                                aUpdatedCategory.getCreatedAt())
                                                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                                                && Objects.isNull(aUpdatedCategory.getDeletedAt())));
        }

        @Test
        public void givenACommandWithInvalidID_whenCallsUpdateCategory_shouldReturnNotFoundException() {
                final var expectedName = "Filmes";
                final var expectedDescription = "A categoria mais assistida";
                final var expectedIsActive = false;
                final var expectedId = "123";
                final var expectedErrorCount = 1;
                final var expectedErrorMessage = "Not found category with ID 123";

                final var aCommand = UpdateCategoryCommand.with(
                                expectedId,
                                expectedName,
                                expectedDescription,
                                expectedIsActive);

                when(categoryGateway.findById(eq(CategoryID.from(expectedId))))
                                .thenReturn(Optional.empty());

                final var actualException = Assertions.assertThrows(DomainException.class,
                                () -> useCase.execute(aCommand));

                Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
                Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

                Mockito.verify(categoryGateway, times(1)).findById(eq(CategoryID.from(expectedId)));

                Mockito.verify(categoryGateway, times(0)).update(any());
        }
}
