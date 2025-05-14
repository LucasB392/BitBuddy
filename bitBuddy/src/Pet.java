import java.io.File;

/**
 * Class handling pet object
 * <br><br>
 * This class contains attributes for the current pet and these attributes can be updated according to the pet's state.<br><br>
 * 
 * @version 1.0
 * @author Lucas
 */

public class Pet {
    /** private variable for pet's health */
    private int health;
    /** private variable for pet's name */
    private String name;
    /** private variable for pet's age */
    private int age;
    /** private variable for pet's hunger */
    private int hunger;
    /** private variable for pet's happiness */
    private int happiness;
    /** private variable for pet's sleep */
    private int sleep;
    /** private variable for pet's size */
    private String size;
    /** private variable for the pet's current state */
    private String status;
    /** private variable for the type of pet */
    private String type;
    /** private variable for the save file if this is the first time playing */
    public boolean firstSave;
    /** public variable for the game score */
    private int score;
    /** an array of the player's inventory */
    ItemArray inv = new ItemArray();

    /** private variable that checks the pet's fullness level */
    private long hungerCounter;
    /** private variable that checks the pet's sleep level */
    private long sleepCounter;
    /** private variable that checks the pet's happiness level */
    private long happinessCounter;
    /** private variable that checks the pet's health level */
    private long healthCounter;



    
    // Constructor
    /** 
     * Initialize the statistics of the pet. The player will select a pet type and their name.
     * 
     * @param name the pet's name chosen by the player
     * @param type the pet's type selected by the player
     */
    public Pet(String name, String type) { // for creating new pet
        this.name = name;
        this.age = 0;
        this.type = type;
        this.health = 100;
        this.hunger = 100;
        this.happiness = 100;
        this.sleep = 100;
        this.size = "Small";
        this.status = "Happy";
        this.firstSave = true;
        File food = new File("food.png");
        inv.addObj(food, "food", "for eating", 0, 0, 0, 0, 0);
        this.score = 100;
    }

    //Constructor Overload
    /**
     * For loading previous pet save into pet class instance.
     * 
     * @param name the pet's name chosen by the player
     * @param type the pet's type selected by the player
     * @param age the age of the pet
     */
    public Pet(String name, String type, int age, int score){ // for loading previous pet save into pet class instance
        this.name = name;
        this.age = age;
        this.type = type;
        this.firstSave = false;
        this.score = score;
    }

    // Getter Methods
    /**
     * Returns the name of the pet.
     * 
     * @return the name of the pet
     */
    public String getName() { 
    	return name; 
    	}
    /**
     * Returns the age of the pet.
     * 
     * @return the age of the pet
     */
    public int getAge() {
    	return age; 
    	}
    /**
     * Returns the pet's health.
     * 
     * @return pet's current health
     */
    public int getHealth() { 
    	return health; 
    	}
    /**
     * Returns the pet's hunger.
     * 
     * @return pet's current hunger
     */
    public int getHunger() { 
    	return hunger; 
    	}
    /**
     * Returns the pet's happiness.
     * 
     * @return pet's current happiness
     */
    public int getHappiness() { 
    	return happiness; 
    	}
    /**
     * Returns the pet's sleep.
     * 
     * @return pet's current sleep
     */
    public int getSleep() { 
    	return sleep; 
    	}
    /**
     * Returns the pet's size.
     * 
     * @return the pet's size
     */
    public String getSize() { 
    	return size; 
    	}
    /**
     * Return's the pet's state.
     * 
     * @return the pet's current state
     */
    public String getStatus() { 
    	return status; 
    	}
    /**
     * Returns the pet's type.
     * 
     * @return the type of pet
     */
    public String getType(){
        return type;
    }
    /**
     * Returns the game's score.
     * 
     * @return the score of game
     */
    public int getScore(){
        return score;
    }

    // Setter Methods
    /**
     * Update the pet's health.
     * 
     * @param health to update the pet's current health level
     */
    public void setHealth(int health) { 
    	this.health = health; 
    	}
    /**
     * Update the pet's hunger.
     * 
     * @param hunger to update the pet's current hunger level
     */
    public void setHunger(int hunger) { 
    	this.hunger = hunger; 
    	}
    /**
     * Update the pet's happiness.
     * 
     * @param happiness to update the pet's current happiness level
     */
    public void setHappiness(int happiness) { 
    	this.happiness = happiness; 
    	}
    /**
     * Update the pet's sleep.
     * 
     * @param sleep to update the pet's current sleep level
     */
    public void setSleep(int sleep) { 
    	this.sleep = sleep; 
    	}
    /**
     * Update the pet's state
     * 
     * @param status to update the current state of the pet
     */
    public void setStatus(String status) { 
    	this.status = status; 
    	}
    /**
     * Update the type of pet chosen
     * 
     * @param type to update the selected pet's type
     */
    public void setType(String type){
        this.type = type;
    }
    /**
     * Update the score of pet chosen
     * 
     * @param type to update the selected pet's score
     */
    public void setScore(int score){
        this.score = score;
    }


    // Effects Methods

    /**
     * Update the counters of the vital statistics over time.
     * 
     * @param time which will increase during gameplay
     */
    public void setCounter(long time){
        this.hungerCounter = time;
        this.sleepCounter = time;
        this.healthCounter = time;
        this.happinessCounter = time;
    }

    /**
     * Update the levels of the vital statistics over time.
     * 
     * @param time which will increase during gameplay
     */
    public void petCounter(long time) {
        // Only reduce sleep if the pet is not sleeping.
        if (!getStatus().equalsIgnoreCase("Sleeping") && time - sleepCounter >= 3000) {
            sleep = Math.max(0, sleep - 10);
            sleepCounter = time;
        }

        if (time - hungerCounter >= 4000) {
            hunger = Math.max(0, hunger - 10);
            hungerCounter = time;
        }

        // Health declines if hunger or sleep is low
        if (hunger < 30 || sleep < 30) {
            health = Math.max(0, health - 2);
        }

        // Happiness declines if hunger or sleep is low
        if (hunger < 30 || sleep < 30) {
            happiness = Math.max(0, happiness - 1);
        }
    }



}

