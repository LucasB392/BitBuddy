import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class CommandsTest {

    private Commands commands;
    private Pet pet;
    private arrayObject testItem;
    
    @BeforeEach
void setUp() {
    pet = new Pet("Testy", "dog", 2, 0);        // initialize pet first
    pet.setStatus("Normal");                   
    pet.inv = new ItemArray();                 // give it inventory

    // Add test item
    pet.inv.addObj(null, "Treat", "Test desc", 0, 20, 0, 25, 15);

    commands = new Commands(pet);              // then create Commands
}


    @Test
    void testFeedPet() {
        int before = pet.getHunger();
        commands.feedPet("Treat");
        assertTrue(pet.getHunger() > before);
        assertEquals("Fed, 0", pet.getStatus());
    }

    @Test
    void testGivePetPresent() {
        int before = pet.getHappiness();
        commands.givePetPresent("Treat");
        assertTrue(pet.getHappiness() > before);
        assertEquals("Received Gift", pet.getStatus());
    }

    @Test
    void testVisitVet() {
        int before = pet.getHealth();
        commands.visitVet("Treat");
        assertTrue(pet.getHealth() > before);
        assertEquals("Angry", pet.getStatus()); // Temp status before Timer resets
    }

    @Test
    void testPlayWithPet() {
        int before = pet.getHappiness();
        commands.playWithPet();
        assertTrue(pet.getHappiness() > before);
        assertEquals("Playing", pet.getStatus());
    }

    @Test
    void testExercisePet() {
        pet.setHealth(50);
        pet.setHunger(50);
        pet.setSleep(50);

        commands.exercisepet();

        assertEquals(0, pet.getScore());
        assertTrue(pet.getHealth() > 50);
        assertTrue(pet.getHunger() < 50);
        assertTrue(pet.getSleep() < 50);
        assertEquals("Exercising", pet.getStatus());
    }

    @Test
    void testMakePetSpeakSetsStatus() {
        commands.makePetSpeak();
        assertEquals("Speaking", pet.getStatus());
        // We skip verifying sound for testability
    }
}
