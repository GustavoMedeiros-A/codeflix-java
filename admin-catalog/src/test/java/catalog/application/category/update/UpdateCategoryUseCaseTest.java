package catalog.application.category.update;

import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import catalog.domain.category.Category;
import catalog.domain.category.CategoryGateway;

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
                                .thenReturn(Optional.of(Category.with(aCategory)));

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

}
