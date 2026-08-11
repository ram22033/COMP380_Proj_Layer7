package layer8project;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;


public class UserProgressTest {
    @Test
    void testUnlockModule() {

        UserProgress progress = new UserProgress("TESTUSER");
        boolean result = progress.unlockModule("M001");
        assertTrue(result);
        assertTrue(progress.isModuleUnlocked("M001"));
    }
    @Test
    void testUnlockModuleTwice() {
        UserProgress progress = new UserProgress("TESTUSER");
        boolean firstUnlock = progress.unlockModule("M001");
        boolean secondUnlock = progress.unlockModule("M001");
        assertTrue(firstUnlock);
        assertFalse(secondUnlock);
        assertEquals(1, progress.getUnlockedModuleCount());
    }

}
