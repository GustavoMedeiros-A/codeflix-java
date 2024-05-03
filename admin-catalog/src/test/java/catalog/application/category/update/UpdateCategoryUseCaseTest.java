package catalog.application.category.update;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.AdditionalAnswers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import catalog.domain.category.Category;
import catalog.domain.category.CategoryGateway;
import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

@ExtendWith(MockitoException.class)
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
        final var aCategory = Category.newCategory("Old movie name", null, true);

        final var expectedName = "Movies";
        final var expectedDescription = "The Category";
        final var expectedIsActive = true;

        final var expectedCategoryId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedCategoryId.getValue(),
                expectedName,
                expectedDescription,
                expectedDescription);

        // From Mockito
        when(this.categoryGateway.findById(Mockito.eq(expectedCategoryId)))
                .thenReturn(Optional.of(aCategory));

        when(this.categoryGateway.update(Mockito.any()))
                .thenAnswer(AdditionalAnswers.returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Mockito.verify(categoryGateway, times(1)).findById(Mockito.eq(expectedCategoryId));

        Mockito.verify(categoryGateway, times(1)).update(argThat(aUpdatedCategory -> {
            return Objects.equals(expectedName, aUpdatedCategory.getName())
                    && Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                    && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                    && Objects.equals(expectedCategoryId, aUpdatedCategory.getId())
                    && Objects.equals(aCategory, aUpdatedCategory.getCreatedAt())
                    && aCategory.getCreatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                    && Objects.isNull(aUpdatedCategory.getDeletedAt());
        }));
    }

}
