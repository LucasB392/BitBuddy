import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class ItemArrayTest {

    ItemArray itemArray;

    @BeforeEach
    void setup() {
        itemArray = new ItemArray();
    }

    @Test
    void testAddObjAndGetSize() {
        itemArray.addObj(new File("apple.png"), "Apple", "Red fruit", 1, 10, 5, 8, 7);
        assertEquals(1, itemArray.getSize());
    }

    @Test
    void testGetItemReturnsCorrectObject() {
        itemArray.addObj(new File("ball.png"), "Ball", "Bouncy toy", 2, 0, 0, 0, 10);
        arrayObject obj = itemArray.getItem("Ball");
        assertNotNull(obj);
        assertEquals("Ball", obj.getName());
        assertEquals(10, obj.getHappiness());
    }

    @Test
    void testRemoveObjByName() {
        itemArray.addObj(new File("apple.png"), "Apple", "Red fruit", 1, 10, 5, 8, 7);
        itemArray.addObj(new File("ball.png"), "Ball", "Bouncy toy", 2, 0, 0, 0, 10);
        itemArray.removeObj("Apple");
        assertEquals(1, itemArray.getSize());
        assertNull(itemArray.getItem("Apple"));
    }

    @Test
    void testGetSaveAndLoadArray() {
        itemArray.addObj(new File("item1.png"), "Item1", "Desc1", 0, 1, 2, 3, 4);
        itemArray.addObj(new File("item2.png"), "Item2", "Desc2", 1, 5, 6, 7, 8);

        String save = itemArray.getSave();

        ItemArray loadedArray = new ItemArray();
        loadedArray.loadArray(save);

        assertEquals(2, loadedArray.getSize());
        assertEquals("Item1", loadedArray.getItem("Item1").getName());
        assertEquals("Item2", loadedArray.getItem("Item2").getName());
        assertEquals(8, loadedArray.getItem("Item2").getHappiness());
    }

    @Test
    void testGetItemReturnsNullIfNotFound() {
        itemArray.addObj(new File("banana.png"), "Banana", "Yellow fruit", 3, 2, 2, 5, 3);
        assertNull(itemArray.getItem("Pizza"));
    }
}
