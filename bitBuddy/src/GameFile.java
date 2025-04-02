import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class GameFile { 


     public static Pet loadedPet;
     public static ItemArray loadedInventory = new ItemArray();
    
    private static boolean backgroundMusic = true;

    private static boolean parentControls = true;


    private static int saveID = 0; // 0 - 2 (choosing from 3 save slots)
    private static int score = 0;

    long timePLayed = 0;

    public static void main(String[] args) {
        System.out.println("runs");
        Pet test = new Pet("Bow","bowser",21);
        test.setHappiness(19);
        test.setHealth(1);
        test.setSleep(54);
        test.setStatus("terminal");
        test.setHunger(97);
        ItemArray testbox = new ItemArray();
        testbox.addObj(new File("pizza.png"), "Pizza", "Yummy food that fills you up."
        , 0, 10, 33, 21, 23);
        testbox.addObj(new File("pizza.png"), "Pizza", "Yummy food that fills you up."
        , 0, 10, 33, 21, 23);
        testbox.addObj(new File("pizza.png"), "Pizza", "Yummy food that fills you up."
        , 0, 10, 33, 21, 23);
        testbox.addObj(new File("pizza.png"), "Pizza", "Yummy food that fills you up."
        , 0, 10, 33, 21, 23);
        testbox.addObj(new File("potion.png"), "Potion", "Magical potion to restore health."
        , 0, 30, 0, 0, 23);
        testbox.addObj(new File("bone.png"), "Bone", "A classic toy your pet loves."
        , 2, 10, 33, 21, 23);
        String inventorySave = testbox.getSave();
        System.out.println("inventorySave: "+inventorySave);
        saveGame(0,formatPetSave(test), inventorySave);
    }

    public static String formatPetSave(Pet p){
// "EMPTY/NAME/AGE/TYPE/HUNGER/HAPPINESS/SLEEP/HEALTH/STATUS"
        String saveFormat = "FULL/" + p.getName()  + "/" + p.getAge() + "/" + p.getType() + "/" + p.getHunger() + "/" + p.getHappiness() + "/" + p.getSleep() + "/" + p.getHealth() + "/" + p.getStatus();
        return saveFormat;
    }

    public static int getSaveID() {
        return saveID;  
    }

    public static int getScore() {
        return score;  
    }

    public static void updateScore(int newScore) {
        score = newScore;
    }

    public static void deleteSave(int saveSlot){
        System.out.println("Deleting save " + saveSlot + ".");
        saveGame(saveSlot,"EMPTY", "EMPTY");
    }




    public static void saveGame(int saveSlot, String gameData, String invenData) {
        List<String> lines = new ArrayList<>();
        lines.add("");
        lines.add("");
        lines.add("");
        lines.add("");
        lines.add("");
        lines.add("");
        saveSlot = saveSlot*2;
        int iter = 0;

        System.out.println("saveSlot: " + saveSlot + "\ngameData: " + gameData + "\ninvenData: " + invenData);

        try (BufferedReader reader = new BufferedReader(new FileReader("GameSaves.csv"))) {
            String line;
            while ((line = reader.readLine()) != null && iter < 6) {
                lines.set(iter, line);
                iter += 1;

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Creating new file.");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (saveSlot < lines.size()) {
            lines.set(saveSlot, gameData);
            lines.set(saveSlot + 1, invenData);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("GameSaves.csv"))) {
            for (String l : lines) {
                writer.write(l);
                writer.newLine();
            }
            System.out.println("Game Saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }











    
    public static String inputName(){
        return "No Save";
    }
    public static String selectType(){
        return "selectedType";
    }




    public static void loadGame(int line) {
        String input = loadLine("GameSaves.csv",line*2);
        System.out.println("INput: " + input);
        String[] file = input.split("/");
        String invent = loadLine("GameSaves.csv",line*2+1);
        System.out.println("Raw slot " + line + ": [" + input + "]");

        if (file[0].equals("EMPTY") || input == null || input.isBlank() || !input.contains("/")) {  // creates new pet if slot is empty. need to create a way to select pet name
            System.out.println("Empty Save Slot Selected. Creating new Pet");
            // Prompt user to select pet type and give it a name
            String selectedType = selectType();
            String givenName = inputName();
            Pet newPetSave = new Pet(givenName,selectedType);
            loadedPet = newPetSave;
            
            loadedInventory.addObj(new File("pizza.png"), "Pizza", "Yummy food that fills you up."
            , 0, 10, 33, 21, 23);
            loadedInventory.addObj(new File("potion.png"), "Potion", "Magical potion to restore health."
            , 0, 30, 0, 0, 23);
            loadedInventory.addObj(new File("bone.png"), "Bone", "A classic toy your pet loves."
            , 2, 10, 33, 21, 23);
            loadedPet.inv = loadedInventory;
        }else{ // loads previous save if one has been made before
        

        // 0 - EMPTY/FULL 
        // 1 - NAME
        // 2 - AGE 
        // 3 - TYPE
        // 4 - HUNGER
        // 5 - HAPPINESS
        // 6 - SLEEP
        // 7 - HEALTH
        // 8 - STATUS

            //Pet(name, type, age)
            loadedPet = new Pet(file[1],file[3],Integer.parseInt(file[2]));
            loadedPet.setHunger(Integer.parseInt(file[4]));
            loadedPet.setHappiness(Integer.parseInt(file[5]));
            loadedPet.setSleep(Integer.parseInt(file[6]));
            loadedPet.setHealth(Integer.parseInt(file[7]));
            loadedPet.setStatus(file[8]);
            loadedInventory.loadArray(invent);
            loadedPet.inv = loadedInventory;

        }



        System.out.println("Name: " + loadedPet.getName());
        System.out.println("Age: " + loadedPet.getAge());
        System.out.println("Health: " + loadedPet.getHealth());        
    }

    public static String loadLine(String fileName, int lineNumber) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int currentLine = 0;
            while ((line = reader.readLine()) != null) {
                if (currentLine == lineNumber) {
                    return line;
                }
                currentLine++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
