package catalog.domain.category;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import catalog.domain.exceptions.DomainException;
import catalog.domain.validation.handler.ThrowsValidationHandler;

public class CategoryTest {

    @Test
    public void givenValidParam_whenCallNewCategory_thenInstantiateACategory() {
        final var expectedName = "Movies";
        final var expectedDescription = "Category";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var actualException = assertThrows(DomainException.class,
                () -> actualCategory.validate(new ThrowsValidationHandler()));

        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenAnEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveError() {
        final String expectedName = "  ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var actualException = assertThrows(DomainException.class,
                () -> actualCategory.validate(new ThrowsValidationHandler()));

        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void given_AnInvalidNameLenghtLessThat3_when_CallNewCategoryAndValidate_then_ShouldReceiveError() {
        final String expectedName = "Na ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var actualException = assertThrows(DomainException.class,
                () -> actualCategory.validate(new ThrowsValidationHandler()));

        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void given_AnInvalidNameLenghtMoreThan255_when_CallNewCategoryAndValidate_then_ShouldReceiveError() {
        final String expectedName = """
                            As simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's
                            standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to
                            make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting,
                            remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem
                             Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.
                """;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var actualException = assertThrows(DomainException.class,
                () -> actualCategory.validate(new ThrowsValidationHandler()));

        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void givenValidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldReturnNotException() {
        final var expectedName = "Movies";
        final var expectedDescription = " ";
        final var expectedIsActive = true;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        assertNotNull(actualCategory);
        assertNotNull(actualCategory.getId());
        assertEquals(expectedName, actualCategory.getName());
        assertEquals(expectedDescription, actualCategory.getDescription());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertNotNull(actualCategory.getCreatedAt());
        assertNotNull(actualCategory.getUpdatedAt());
        assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldReceiveOK() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        // Pass is Active as False need to set deleted_at as default
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactivate() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        // Cria como true e espera que ele fique falso

        final var aCategory = Category.newCategory(expectedName, expectedDescription, true);
        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        assertTrue(aCategory.isActive());
        assertNull(aCategory.getDeletedAt());

        final var actualCategory = aCategory.deactivate();

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt)); // Verificar se a data foi atualizada
        // Pass is Active as False need to set deleted_at as default
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidInactiveCategory_whenCallActivate_thenReturnCategoryActivate() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        // Cria como true e espera que ele fique falso

        final var aCategory = Category.newCategory(expectedName, expectedDescription, false);
        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();
        final var createdAt = aCategory.getCreatedAt();

        assertFalse(aCategory.isActive());
        assertNotNull(aCategory.getDeletedAt());

        final var actualCategory = aCategory.activate();

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt)); // Verificar se a data foi atualizada
        // Pass is Active as False need to set deleted_at as default
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        // Cria como true e espera que ele fique falso
        final var aCategory = Category.newCategory("Old name", "Old category", expectedIsActive);
        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();
        final var createdAt = aCategory.getCreatedAt();

        final var updatedCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), updatedCategory.getId());
        Assertions.assertEquals(expectedName, updatedCategory.getName());
        Assertions.assertEquals(expectedDescription, updatedCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, updatedCategory.isActive());
        Assertions.assertEquals(createdAt, updatedCategory.getCreatedAt());
        Assertions.assertTrue(updatedCategory.getUpdatedAt().isAfter(updatedAt)); // Verificar se a data foi atualizada
        // Pass is Active as False need to set deleted_at as default
        Assertions.assertNull(updatedCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateToInactive_thenReturnCategoryUpdated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        // Cria como true e espera que ele fique falso
        final var aCategory = Category.newCategory("Old name", "Old category", expectedIsActive);
        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();
        final var createdAt = aCategory.getCreatedAt();

        final var updatedCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), updatedCategory.getId());
        Assertions.assertEquals(expectedName, updatedCategory.getName());
        Assertions.assertEquals(expectedDescription, updatedCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, updatedCategory.isActive());
        Assertions.assertEquals(createdAt, updatedCategory.getCreatedAt());
        Assertions.assertTrue(updatedCategory.getUpdatedAt().isAfter(updatedAt)); // Verificar se a data foi atualizada
        // Pass is Active as False need to set deleted_at as default
        assertNotNull(aCategory.getDeletedAt());
        assertFalse(aCategory.isActive());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateWithInvalidParam_thenReturnCategoryUpdated() {
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        // Cria como true e espera que ele fique falso
        final var aCategory = Category.newCategory("Old name", "Old category", expectedIsActive);
        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = aCategory.getUpdatedAt();
        final var createdAt = aCategory.getCreatedAt();

        final var updatedCategory = aCategory.update(expectedName, expectedDescription, expectedIsActive);

        // Não valida para não lançar exception
        // Assertions.assertDoesNotThrow(() -> aCategory.validate(new
        // ThrowsValidationHandler()));

        Assertions.assertEquals(aCategory.getId(), updatedCategory.getId());
        Assertions.assertEquals(expectedName, updatedCategory.getName());
        Assertions.assertEquals(expectedDescription, updatedCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, updatedCategory.isActive());
        Assertions.assertEquals(createdAt, updatedCategory.getCreatedAt());
        Assertions.assertTrue(updatedCategory.getUpdatedAt().isAfter(updatedAt)); // Verificar se a data foi atualizada
        // Pass is Active as False need to set deleted_at as default
        assertNull(aCategory.getDeletedAt());
        assertTrue(aCategory.isActive());
    }

}
