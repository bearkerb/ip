package lucid;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class ParserTest {
    @Test
    public void parseDateAndTimeString_success() throws DateTimeParseException {
        assertArrayEquals(new String[] {"2025-08-19", null}, Parser.parseDateTimeString("2025-08-19"));
        assertArrayEquals(new String[] {"2025-11-24", "1735"}, Parser.parseDateTimeString("2025-11-24-1735"));
    }

    @Test
    public void parseDateAndTimeString_invalidFormat_exceptionThrown() {
        try {
            assertArrayEquals(new String[] {"2025-08-19", null}, Parser.parseDateTimeString("2025/08/19"));
            assertArrayEquals(new String[] {"2025-11-24", "1735"}, Parser.parseDateTimeString("2025/11/24 1735"));
            fail();
        } catch (DateTimeParseException e) {
            assertEquals("I'm sorry! Something went wrong with the date and time", e.getMessage());
        }
    }

}
