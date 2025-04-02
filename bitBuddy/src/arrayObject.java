import java.io.File;

public class arrayObject {
    
    String name;
    String description;
    int objType;
    int modHealth;
    int modSleep;
    int modFullness;
    int modHappiness;
    File sprite;

    public void setSprite(File newSprite){
        sprite = newSprite;
    }

    public void setName(String newName){
        name = newName;
    }

    public void setDescription(String newDis){
        description = newDis;
    }

    public void setType (int newType){
        objType = newType;
    }

    public void setHealth(int newHealth){
        modHealth = newHealth;
    }

    public void setSleep(int newSleep){
        modSleep = newSleep;
    }

    public void setFullness(int fullness){
        modFullness = fullness;
    }

    public void setHappiness(int happiness){
        modHappiness = happiness;
    }

    public String getName(){
        return name;
    }

    public String getDescription(){
        return description;
    }

    public int getType(){
        return objType;
    }

    public int getHealth(){
        return modHealth;
    }

    public int getSleep(){
        return modSleep;
    }

    public int getFullness(){
        return modFullness;
    }

    public int getHappiness(){
        return modHappiness;
    }

    public File getSprite(){
        return sprite;
    }

}