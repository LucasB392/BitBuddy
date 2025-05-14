import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Class handling game data manipulation, loading, and saving
 * <br><br>
 * This class is responsible for saving the game. It collects data, stores into save files, and can be loaded for future gaming sessions.<br><br>
 * 
 * @version 1.0
 * @author Lucas Brown
 */

public class GameFile { 

	/** private variable representing the start time */
    public int startTime;
    /** private variable representing the end time */
    public int endTime;

    /** variable that represents the pet object */
     public static Pet loadedPet;
    /** variable that represents the player's inventory */ 
     public static ItemArray loadedInventory = new ItemArray();
    
    /** boolean variable that checks if music is enabled */
    private static boolean backgroundMusic = true;

    /** boolean variable that checks if parental limitations are enabled */
    private static boolean parentControls = true;

    /** private integer that represents the save slot */
    private static int saveID = 0; // 0 - 2 (choosing from 3 save slots)
    /** private integer that represents the score */
    private static int score = 0;
    
    /** private integer that represents the total play time */
    long timePLayed = 0;

    /**
     * Main method with test run
     * @param args
     */
    public static void main(String[] args) {
        deleteSave(0);
        deleteSave(1);
        deleteSave(2);
    }

    /**
     * Method that returns string with pet save data
     * 
     * @param p representing the pet object
     * @return the save format following "EMPTY/NAME/AGE/TYPE/HUNGER/HAPPINESS/SLEEP/HEALTH/STATUS"
     */
    public static String formatPetSave(Pet p){
        String saveFormat = "FULL/" + p.getName()  + "/" + p.getAge() + "/" + p.getType() + "/" + p.getHunger() + "/" + p.getHappiness() + "/" + p.getSleep() + "/" + p.getHealth() + "/" + p.getStatus() + "/" + p.getScore();
        return saveFormat;
    }

    /**
     * Method that returns save file ID
     * 
     * @return the integer that represents the save file
     */
    public static int getSaveID() {
        return saveID;  
    }

    /**
     * Retrieve game score
     * 
     * @return the score saved on the file
     */
    public static int getScore() {
        return loadedPet.getScore();  
    }

    /**
     * Update game score
     * 
     * @param newScore the new score that will replace the existing score
     */
    public static void updateScore(int newScore) {
        score = newScore;
        loadedPet.setScore(newScore);
    }

    /**
     * Delete save file
     * 
     * @param saveSlot the save file selected to be deleted
     */
    public static void deleteSave(int saveSlot){
        System.out.println("Deleting save " + saveSlot + ".");
        saveGame(saveSlot,"EMPTY", "");
    }

    /**
     * Save the player's progress to a save slot.
     * 
     * @param saveSlot the save slot selected that will contain the data
     * @param gameData the data containing the player's progress
     * @param invenData the data containing the player's inventory
     * @throws an IOException if the save file cannot be found
     * @throws an IOException if a file cannot be written to
     */
    public static void saveGame(int saveSlot, String gameData, String invenData) {
        List<String> lines = new ArrayList<>();
        List<String> newInvenLines = Arrays.asList(invenData.split("\n"));
        int currentSlot = -1;
        int startIndex = -1;
    
        // Load current file
        try (BufferedReader reader = new BufferedReader(new FileReader("GameSaves.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Save file not found, starting fresh.");
        }
    
        // Locate the slot
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith("FULL") || line.startsWith("EMPTY")) {
                currentSlot++;
                if (currentSlot == saveSlot) {
                    startIndex = i;
                    break;
                }
            }
        }
    
        // Clear the old save (pet info + inventory)
        if (startIndex != -1) {
            int endIndex = startIndex + 1;
            while (endIndex < lines.size() &&
                   !lines.get(endIndex).startsWith("FULL") &&
                   !lines.get(endIndex).startsWith("EMPTY")) {
                endIndex++;
            }
    
            // Remove the entire block
            for (int i = endIndex - 1; i >= startIndex; i--) {
                lines.remove(i);
            }
    
            // Insert new save
            lines.add(startIndex, gameData);
            lines.addAll(startIndex + 1, newInvenLines);
        } else {
            // Slot doesn't exist yet: fill in missing slots
            while (++currentSlot < saveSlot) {
                lines.add("EMPTY");
            }
            lines.add(gameData);
            lines.addAll(newInvenLines);
        }
    
        // Save back to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("GameSaves.csv"))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
            System.out.println("Game saved to slot " + saveSlot);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    



    /**
     * Empty Save name
     * 
     * @return a string stating the save slot is empty
     */
    public static String inputName(){
        return "No Save";
    }
    
    /**
     * Empty save pet type
     * 
     * @return a string stating the selected type
     */
    public static String selectType(){
        return "selectedType";
    }



/**
 * Load full save slot (game data + inventory)
 * 
 * @param saveSlot the save slot selected that will contain the data
 */
public static void loadGame(int saveSlot) {
    List<String> saveData = loadLineRange("GameSaves.csv", saveSlot);
    if (saveData == null || saveData.isEmpty()) {
        System.out.println("No save data found.");
        return;
    }

    String input = saveData.get(0);
    System.out.println("Input: " + input);
    String[] file = input.split("/");

    // Create a fresh inventory object every time
    ItemArray newInventory = new ItemArray();

    // Check for empty slot or invalid data
    if (file[0].equals("EMPTY") || input == null || input.isBlank() || !input.contains("/")) {
        System.out.println("Empty Save Slot Selected. Creating new Pet");
        String selectedType = selectType();
        String givenName = inputName();
        Pet newPetSave = new Pet(givenName, selectedType);
        newPetSave.inv = newInventory;
        loadedPet = newPetSave;
        loadedInventory = newInventory;
    } else {
        // Create and load existing pet
        Pet pet = new Pet(file[1], file[3], Integer.parseInt(file[2]), Integer.parseInt(file[9]));
        pet.setHunger(Integer.parseInt(file[4]));
        pet.setHappiness(Integer.parseInt(file[5]));
        pet.setSleep(Integer.parseInt(file[6]));
        pet.setHealth(Integer.parseInt(file[7]));
        pet.setStatus(file[8]);

        // Load inventory into fresh object
        List<String> inventoryLines = saveData.subList(1, saveData.size());
        StringBuilder inventoryData = new StringBuilder();
        for (String line : inventoryLines) {
            inventoryData.append(line).append("\n");
        }
        newInventory.loadArray(inventoryData.toString().trim());
        pet.inv = newInventory;

        loadedPet = pet;
        loadedInventory = newInventory;
    }

    // Debug
    System.out.println("Name: " + loadedPet.getName());
    System.out.println("Age: " + loadedPet.getAge());
    System.out.println("Health: " + loadedPet.getHealth());
}



/**
 * Load all lines of a save slot (gameData + all inventory lines)
 * 
 * @param fileName the name of the file
 * @param saveSlot the save slot selected that will contain the data
 * @return the lines found on the save slot
 */
public static List<String> loadLineRange(String fileName, int saveSlot) {
    List<String> result = new ArrayList<>();
    int currentSlot = -1;
    boolean inTargetSlot = false;

    try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("FULL") || line.startsWith("EMPTY")) {
                currentSlot++;

                // If we were already reading the target slot, we're done
                if (inTargetSlot) {
                    break;
                }

                // Check if this is the slot we want to start reading
                if (currentSlot == saveSlot) {
                    inTargetSlot = true;
                    result.add(line); // Add the gameData line
                }
            } else if (inTargetSlot) {
                result.add(line); // Inventory line
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    return result;
}

}
