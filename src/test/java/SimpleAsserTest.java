import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class SimpleAsserTest {
    @Test
    public void testTrue() {
        String one = "This message is longer then 15";
// System.out.println(one.length());
        assertTrue(one.length() > 15, "message is shorter then 15");
    }
    @Test
    public void testFalse() {
        String two = "Small message";
        assertTrue(two.length() > 15, "message is shorter then 15");
    }
}
