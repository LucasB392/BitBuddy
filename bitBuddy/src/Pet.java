
import java.io.File;

public class Pet {
    // Attributes
    private int health;
    private String name;
    private int age;
    private int hunger;
    private int happiness;
    private int sleep;
    private String size;
    private String status;
    private String type;
    public boolean firstSave;

    ItemArray inv = new ItemArray();


    private long hungerCounter;
    private long sleepCounter;
    private long happinessCounter;
    private long healthCounter;



    
    // Constructor
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
    }

    //Constructor Overload
    public Pet(String name, String type, int age){ // for loading previous pet save into pet class instance
        this.name = name;
        this.age = age;
        this.type = type;
        this.firstSave = false;
    }

    // Getter Methods
    public String getName() { 
    	return name; 
    	}
    public int getAge() {
    	return age; 
    	}
    public int getHealth() { 
    	return health; 
    	}
    public int getHunger() { 
    	return hunger; 
    	}
    public int getHappiness() { 
    	return happiness; 
    	}
    public int getSleep() { 
    	return sleep; 
    	}
    public String getSize() { 
    	return size; 
    	}
    public String getStatus() { 
    	return status; 
    	}
    public String getType(){
        return type;
    }

    // Setter Methods
    public void setHealth(int health) { 
    	this.health = health; 
    	}
    public void setHunger(int hunger) { 
    	this.hunger = hunger; 
    	}
    public void setHappiness(int happiness) { 
    	this.happiness = happiness; 
    	}
    public void setSleep(int sleep) { 
    	this.sleep = sleep; 
    	}
    public void setStatus(String status) { 
    	this.status = status; 
    	}
    public void setType(String type){
        this.type = type;
    }


    // Effects Methods


    public void setCounter(long time){
        this.hungerCounter = time;
        this.sleepCounter = time;
        this.healthCounter = time;
        this.happinessCounter = time;
    }

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
