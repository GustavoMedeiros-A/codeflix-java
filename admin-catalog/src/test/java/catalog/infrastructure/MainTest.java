package catalog.infrastructure;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class MainTest {

    @Test
    public void testMain() {
        assertNotNull(new Main());
        Main.main(new String[] {});
    }

}
