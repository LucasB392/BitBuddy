import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.nio.file.*;
import java.util.List;

public class GameFileTest {

    @BeforeEach
    void setup() {
        // Create a default pet for static loadedPet
        Pet pet = new Pet("Buddy", "Dog", 3, 100);
        GameFile.loadedPet = pet;
        GameFile.updateScore(100);
    }

    @Test
    void testFormatPetSave() {
        Pet testPet = new Pet("Buddy", "Dog", 3, 500);
        testPet.setHunger(5);
        testPet.setHappiness(7);
        testPet.setSleep(6);
        testPet.setHealth(9);
        testPet.setStatus("Happy");

        String result = GameFile.formatPetSave(testPet);
        String expected = "FULL/Buddy/3/Dog/5/7/6/9/Happy/500";
        assertEquals(expected, result);
    }

    @Test
    void testGetScore() {
        assertEquals(100, GameFile.getScore());
    }

    @Test
    void testUpdateScore() {
        GameFile.updateScore(200);
        assertEquals(200, GameFile.getScore());
    }

    @Test
    void testDeleteSaveCreatesEmptySlot() throws IOException {
        // Arrange
        int slot = 1;
        Path path = Paths.get("GameSaves.csv");
        Files.writeString(path, """
            FULL/Test/3/Dog/5/5/5/5/Happy/200
            InventoryItem1
            FULL/Other/3/Cat/5/5/5/5/Sad/150
            """);

        // Act
        GameFile.deleteSave(slot);

        // Assert
        List<String> lines = Files.readAllLines(path);
        assertTrue(lines.stream().anyMatch(l -> l.equals("EMPTY")));
    }

    @Test
    void testLoadGameFromEmptySlotCreatesNewPet() throws IOException {
        // Arrange
        int slot = 0;
        Path path = Paths.get("GameSaves.csv");
        Files.writeString(path, "EMPTY");

        // Act
        GameFile.loadGame(slot);

        // Assert
        assertNotNull(GameFile.loadedPet);
        assertEquals("No Save", GameFile.loadedPet.getName());
        assertEquals("selectedType", GameFile.loadedPet.getType());
    }
}
