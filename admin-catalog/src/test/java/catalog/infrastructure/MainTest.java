package catalog.infrastructure;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    public void testMain() {
        assertNotNull(new Main());
        Main.main(new String[] {});
    }

}
