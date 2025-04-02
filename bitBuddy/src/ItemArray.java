import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemArray {
    // Public attributes
    List<arrayObject> objectData = new ArrayList<>();

    // Add to array of object data
    //Object class; 0 = consumable, 1 = decoration, 2 = utility
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

    // Remove from array of object data
    public void removeObj(String target){
        for (int i = objectData.size()-1; i >= 0; i -= 1){
            String name = objectData.get(i).getName();
             if (target.equals(name)){
                objectData.remove(i);
             }
        }
    }

    // Method to get an item
    public arrayObject getItem(String target) {
        for (int i = 0; i < objectData.size(); i += 1){
             String name = objectData.get(i).getName();
             if (target.equals(name)){
                return objectData.get(i);
             }
        }
        return null;
    }

    // Returns a set of lines, each representing an object. every column will represent an attribute of the row object
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

    // Get amount of objects in inventory
    public int getSize(){
        return objectData.size();
    }


}

