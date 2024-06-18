package catalog.application.category.retrieve.list;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.List;

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
import catalog.domain.category.CategorySearchQuery;
import catalog.domain.pagination.Pagination;

@ExtendWith(MockitoExtension.class)
public class ListCategoriesUseCaseTest {

    @InjectMocks
    private DefaultListCategoriesUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    public void shouldReturnCategoriesWhenCallsListCategoriesValidQuery() {
        final var categories = List.of(
                Category.newCategory("Filmes", null, true),
                Category.newCategory("Series", null, true));

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedOrder = "asc";

        final var aQuery = new CategorySearchQuery(expectedPage, expectedPage, expectedTerms, expectedSort,
                expectedOrder);

        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, categories.size(), categories);

        final var expectedItemsCount = 2;
        final var expectedResult = expectedPagination.map(CategoryListOutput::from);

        Mockito.when(categoryGateway.findAll(eq(aQuery))).thenReturn(expectedPagination);

        final var actualResult = useCase.execute(aQuery);

        assertEquals(expectedItemsCount, actualResult.items().size());

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldThrowsNotFoundExceptionWhenCallGetAllCategories() {
        final var categories = List.<Category>of();

        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";

        final var aQuery = new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort,
                expectedDirection);

        final var expectedPagination = new Pagination<>(expectedPage, expectedPerPage, categories.size(), categories);

        final var expectedItemsCount = 0;
        final var expectedResult = expectedPagination.map(CategoryListOutput::from);

        when(categoryGateway.findAll(eq(aQuery)))
                .thenReturn(expectedPagination);

        final var result = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, result.items().size());
        Assertions.assertEquals(expectedResult, result);
        Assertions.assertEquals(expectedPage, result.currentPage());
        Assertions.assertEquals(expectedPerPage, result.perPage());
        Assertions.assertEquals(categories.size(), result.total());

    }

    @Test
    public void shouldThrowsReturnExceptionWhenGatewayThrowsException() {
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "";
        final var expectedSort = "createdAt";
        final var expectedDirection = "asc";
        final var expectedErrorMessage = "Gateway error";

        final var aQuery = new CategorySearchQuery(expectedPage, expectedPerPage, expectedTerms, expectedSort,
                expectedDirection);

        when(categoryGateway.findAll(eq(aQuery))).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException = assertThrows(IllegalStateException.class, () -> useCase.execute(aQuery));
        assertEquals(expectedErrorMessage, actualException.getMessage());
    }

}
