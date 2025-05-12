import java.io.File;

/**
 * Class for creating objects in inventory array
 * <br><br>
 * This class creates the an array and displays the items in the player's inventory.<br><br>
 * 
 * @version 1.0
 * @author Auntic
 */

public class arrayObject {
    
	//* private variable for item's name */
    String name;
    //* private variable for the item's description */
    String description;
    //* private variable for the type of object */
    int objType;
    //* private variable for the item's contribution to health */
    int modHealth;
    //* private variable for the item's contribution to sleep */
    int modSleep;
    //* private variable for the item's contribution to fullness */
    int modFullness;
    //* private variable for the item's contribution to happiness */
    int modHappiness;
    //* the file containing the image of the item */
    File sprite;

    /**
     * Set object image
     * 
     * @param newSprite the new image representing the item
     */
    public void setSprite(File newSprite){
        sprite = newSprite;
    }

    /**
     * Set object name
     * 
     * @param newName the name of the item
     */
    public void setName(String newName){
        name = newName;
    }

    /**
     * Set object description
     * 
     * @param newDis the description associated with the item
     */
    public void setDescription(String newDis){
        description = newDis;
    }

    /**
     * Set object use Type
     * 
     * @param newType the type of the item
     */
    public void setType (int newType){
        objType = newType;
    }

    /**
     * Set health modification on object use
     * 
     * @param newHealth the health contribution of the item
     */
    public void setHealth(int newHealth){
        modHealth = newHealth;
    }

    /**
     * Set sleep modification on object use
     * 
     * @param newSleep the sleep contribution of the item
     */
    public void setSleep(int newSleep){
        modSleep = newSleep;
    }

    /**
     * Set fullness modification on object use
     * 
     * @param fullness the fullness contribution of the item
     */
    public void setFullness(int fullness){
        modFullness = fullness;
    }

    /**
     * Set happiness modification on object use
     * 
     * @param happiness the happiness contribution of the item
     */
    public void setHappiness(int happiness){
        modHappiness = happiness;
    }

    /**
     * Retrieve object name
     * 
     * @return the name of the item
     */
    public String getName(){
        return name;
    }

    /**
     * Retrieve object description
     * 
     * @return the description of the item
     */
    public String getDescription(){
        return description;
    }

    /**
     * Retrieve object type
     * 
     * @return the type of the item
     */
    public int getType(){
        return objType;
    }

    /**
     * Retrieve object health mod
     * 
     * @return the health modifications of the item
     */
    public int getHealth(){
        return modHealth;
    }

    /**
     * Retrieve object sleep mod
     * 
     * @return the sleep modifications of the item
     */
    public int getSleep(){
        return modSleep;
    }

    /**
     * Retrieve object fullness mod
     * 
     * @return the fullness modifications of the item
     */
    public int getFullness(){
        return modFullness;
    }

    /**
     * Retrieve object happiness mod
     * 
     * @return the happiness modifications of the item
     */
    public int getHappiness(){
        return modHappiness;
    }

    /**
     * Retrieve object sprite file
     * 
     * @return the image of the item
     */
    public File getSprite(){
        return sprite;
    }

}
