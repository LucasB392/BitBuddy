import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Class handling array management
 * <br><br>
 * Objects are added to the array and can be removed.<br><br>
 * 
 * @version 1.0
 * @author Lucas Brown
 */

public class ItemArray {
    /** Public attributes */
    List<arrayObject> objectData = new ArrayList<>();

    /**
     * Adds the required parameters to the array.
     * 
     * @param sprite the image of the pet
     * @param name the name of the pet
     * @param description the description associated with the pet
     * @param objClass the Pet object
     * @param modHealth the pet's health level
     * @param modSleep the pet's sleep level
     * @param modFullness the pet's fullness level
     * @param modHappiness the pet's happiness level
     */
    public void addObj(File sprite, String name, String description, int objClass, int modHealth, int modSleep, int modFullness, int modHappiness){
        arrayObject obj = new arrayObject();
        obj.setSprite(sprite);
        obj.setName(name);  
        obj.setDescription(description);
        obj.setType(objClass);
        obj.setHealth(modHealth);
        obj.setSleep(modSleep);
        obj.setFullness(modFullness);
        obj.setHappiness(modHappiness);
        objectData.add(obj);
    }

    /**
     * Removes the specified target from the array.
     * 
     * @param target which is the required item to remove
     */
    public void removeObj(String target) {
        for (int i = objectData.size() - 1; i >= 0; i--) {
            String name = objectData.get(i).getName();
            if (target.toLowerCase().equals(name.toLowerCase())) {
                objectData.remove(i);
                break; // Stop after removing the first match
            }
        }
    }
    

    /**
     * Searches for an item in the array and returns that object.
     * 
     * @param target which is the required item to locate
     * @return the object if found
     */
    public arrayObject getItem(String target) {
        for (int i = 0; i < objectData.size(); i += 1){
             String name = objectData.get(i).getName();
             if (target.equals(name)){
                return objectData.get(i);
             }
        }
        return null;
    }

    /**
     * Returns a set of lines, each representing an object. every column will represent an attribute of the row object
     * 
     * @return the string containing the attributes of the object.
     */
    public String getSave(){
        String saveFile = "";
        for (int item = 0; item < objectData.size(); item += 1){
            arrayObject curObj = objectData.get(item);
            saveFile = saveFile + curObj.getSprite() + "," + curObj.getName() + "," + curObj.getDescription() + "," + curObj.getType() + "," + curObj.getHealth() +
            "," + curObj.getSleep() + ","+ curObj.getFullness() + "," + curObj.getHappiness() + "\n";
        }
        return saveFile;
    }

    // Is fed the savestring from excel, must be called when creating a new inventory array on game load to load it with objects.
    /**
     * Load the array with objects when the game starts.
     * 
     * @param saved the string containing the attributes of the pet
     */
    public void loadArray(String saved){
        String[] lines = saved.split("\n");
        for (String line: lines){
            if (line.trim().isEmpty()) continue;
            String[] values = line.split(",");
            File sprite = new File(values[0]);
            String name = values[1];
            String description = values[2];
            int objType = Integer.parseInt(values[3]);
            int modHealth = Integer.parseInt(values[4]);
            int modSleep = Integer.parseInt(values[5]);
            int modFullness = Integer.parseInt(values[6]);
            int modHappiness = Integer.parseInt(values[7]);
            addObj(sprite, name, description, objType, modHealth, modSleep, modFullness, modHappiness);

        }
    }

    /**
     * Get amount of objects in inventory.
     * 
     * @return the amount of objects found in the inventory.
     */
    public int getSize(){
        return objectData.size();
    }


}


