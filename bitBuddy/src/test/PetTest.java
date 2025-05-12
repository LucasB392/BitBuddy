import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class PetTest {

    Pet pet;

    @Test
    void testNewPetConstructor() {
        pet = new Pet("Fluffy", "Cat");
        assertEquals("Fluffy", pet.getName());
        assertEquals("Cat", pet.getType());
        assertEquals(0, pet.getAge());
        assertEquals(100, pet.getHealth());
        assertTrue(pet.firstSave);
        assertEquals("Small", pet.getSize());
        assertEquals("Happy", pet.getStatus());
        assertEquals(100, pet.getScore());
        assertEquals(1, pet.inv.getSize()); // food was added
    }

    @Test
    void testLoadedPetConstructor() {
        pet = new Pet("Max", "Dog", 5, 300);
        assertEquals("Max", pet.getName());
        assertEquals("Dog", pet.getType());
        assertEquals(5, pet.getAge());
        assertEquals(300, pet.getScore());
        assertFalse(pet.firstSave);
    }

    @Test
    void testStatSettersAndGetters() {
        pet = new Pet("Test", "Fox");
        pet.setHealth(80);
        pet.setHunger(75);
        pet.setHappiness(60);
        pet.setSleep(90);
        pet.setStatus("Angry");
        pet.setType("Fox");
        pet.setScore(200);

        assertEquals(80, pet.getHealth());
        assertEquals(75, pet.getHunger());
        assertEquals(60, pet.getHappiness());
        assertEquals(90, pet.getSleep());
        assertEquals("Angry", pet.getStatus());
        assertEquals("Fox", pet.getType());
        assertEquals(200, pet.getScore());
    }

    @Test
    void testSetCounterAndDecayOverTime() {
        pet = new Pet("Lazy", "Sloth");
        pet.setCounter(0); // initialize counters
        pet.setSleep(50);
        pet.setHunger(50);
        pet.setHealth(100);
        pet.setHappiness(100);
        pet.setStatus("Normal");

        // Simulate time passing
        pet.petCounter(5000); // should reduce all stats
        assertEquals(40, pet.getSleep());
        assertEquals(40, pet.getHunger());
        assertEquals(100, pet.getHealth()); // -2 from low stats
        assertEquals(100, pet.getHappiness()); // -1 from low stats
    }

    @Test
    void testSleepNotReducedIfSleeping() {
        pet = new Pet("Snoozer", "Dog");
        pet.setSleep(50);
        pet.setStatus("Sleeping");
        pet.setCounter(0);
        pet.petCounter(5000);

        assertEquals(50, pet.getSleep()); // shouldn't go down while sleeping
    }
}
