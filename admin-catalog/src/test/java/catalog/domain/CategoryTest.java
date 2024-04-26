package catalog.domain;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class CategoryTest {

    @Test
    public void testNewCategory() {
        assertNotNull(new Category());
    }
}
