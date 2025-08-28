package lucid;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class StorageTest {
    @Test
    public void lineToEvent_success() {
        Storage storage = new Storage();
        assertEquals("[E][ ] birthday party (from: DECEMBER 12 2025 12:00 to: DECEMBER 12 2025 16:30)",
                storage.lineToEvent("E | NOT DONE | birthday party | 2025-12-12-1200 | 2025-12-12-1630").toString());
        assertEquals("[E][X] birthday party (from: DECEMBER 12 2025 to: DECEMBER 12 2025)",
                storage.lineToEvent("E | DONE | birthday party | 2025-12-12 | 2025-12-12").toString());
    }
}
