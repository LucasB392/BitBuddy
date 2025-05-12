import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
public class GameIntegrationTest {
    Pet pet;
    Commands commands;
    @BeforeEach
    void setUp() {
        // Create a fresh Pet and Commands instance
        pet = new Pet("Buddy", "Dog");
        commands = new Commands(pet);
        GameFile.loadedPet = pet;
        GameFile.loadedInventory = pet.inv;
    }
    @Test
    void testFeedPetAndSaveGame() {
        // Add food item manually
        File dummy = new File("pizza.png");
        pet.inv.addObj(dummy, "Pizza", "Tasty", 0, 0, 0, 25, 0);
        int initialHunger = pet.getHunger();
        commands.feedPet("Pizza");
        // Hunger should have increased
        assertTrue(pet.getHunger() > initialHunger);
        assertEquals("Fed, " + GameFile.getScore(), pet.getStatus());
        // Save pet and inventory
        String petData = GameFile.formatPetSave(pet);
        String invenData = pet.inv.getSave();
        GameFile.saveGame(0, petData, invenData);
        // Reload and check that it's the same
        GameFile.loadGame(0);
        Pet reloaded = GameFile.loadedPet;
        assertEquals(pet.getName(), reloaded.getName());
        assertEquals(pet.getHunger(), reloaded.getHunger());
        assertEquals(pet.getScore(), reloaded.getScore());
    }
    @Test
    void testVetInteractionAndScoreUpdate() {
        // Add medicine
        File dummy = new File("potion.png");
        pet.inv.addObj(dummy, "Potion", "Heals pet", 0, 30, 0, 0, 0);
        pet.setHealth(50);
        commands.visitVet("Potion");
        assertTrue(pet.getHealth() > 50);
        assertEquals("Angry", pet.getStatus());
        assertEquals(GameFile.getScore(), pet.getScore());
    }
    @Test
    void testExerciseAffectsStatsAndScore() {
        pet.setHealth(50);
        pet.setHunger(50);
        pet.setSleep(50);
        pet.setScore(100);
        commands.exercisepet();
        assertEquals(53, pet.getScore()); // 100 + 3
        assertTrue(pet.getHealth() > 50);
        assertTrue(pet.getHunger() < 50);
        assertTrue(pet.getSleep() < 50);
        assertEquals("Exercising", pet.getStatus());
    }
    @AfterEach
    void tearDown() {
        GameFile.deleteSave(0); // Clean test save
    }
}
