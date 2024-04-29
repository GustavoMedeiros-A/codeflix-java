package catalog.domain.category;

public record CategorySearchQuery(
        int page,
        int perPage,
        String terms,
        String sort, // Order by
        String direction // Ascending or descending
) {

}
