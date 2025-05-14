import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Class to issue commands that interact with the pet
 * <br><br>
 * This class is responsible for using commands that can interact with the pet. The user is able to feed the pet, make the pet sleep, provide a gift, etc.<br><br>
 * 
 * @version 1.0
 * @author Lucas Brown
 */

public class Commands {
	/** variable representing the pet object */
    public Pet pet;
    /** points for reward system */
    private int points = 0;

    // Cooldown trackers (in milliseconds)
    /** represents the cooldown for visiting the vet */
    private long lastVetTime = 0;
    /** represents the cooldown for playtime */
    private long lastPlayTime = 0;
    /** 30 seconds cooldown */
    private final long cooldownMillis = 30000;

    /** Maximum sleep value (when pet is fully rested) */
    private final int MAX_SLEEP = 100;

    /**
     * initialize the pet
     * 
     * @param pet the pet object used
     */
    public Commands(Pet pet) {
        this.pet = pet;
        // Inventory setup is now done via pet.inv (presumably initialized in Pet)
    }

    /**
     * Getter for points
     * 
     * @return the current score
     */
    public int getPoints() {
        return pet.getScore();
    }

    /**
     * Check if a command can be used based on the pet's current state.
     * States follow precedence: Dead > Sleeping > Angry > Hungry > Normal.
     * If the pet is Angry, only Gift and Play are allowed.
     * 
     * @param commandName the command the player will use on the pet
     * @return a boolean value stating if that command can be used
     */
    private boolean canUseCommand(String commandName) {
        String state = pet.getStatus();
        return switch (state) {
            case "Dead", "Sleeping" -> false;
            case "Angry" -> commandName.equals("Gift") || commandName.equals("Play");
            // For Hungry and Normal states, all commands are allowed.
            case "Hungry", "Normal" -> true;
            default -> true;
        };
    }

    /**
     * Go to bed: Pet sleeps until its sleep value reaches the maximum.
     * 
     * @throws an InterruptedException if the pet cannot sleep
     */
    public void sleepyPet() {
        if (!canUseCommand("Sleep")) {
            System.out.println("Cannot go to bed in the current state: " + pet.getStatus());
            return;
        }
        pet.setStatus("Sleeping");
        pet.setSleep(0);  // Reset sleep so the while loop actually runs.
        System.out.println("Pet is going to bed and is now sleeping...");

        new Thread(() -> {
            while (pet.getSleep() < MAX_SLEEP) {
                try {
                    Thread.sleep(1000); // simulate time passing (1 second)
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
                // Increase sleep gradually (simulate recharge)
                pet.setSleep(Math.min(pet.getSleep() + 10, MAX_SLEEP));
                System.out.println("Sleeping... Current sleep value: " + pet.getSleep());
            }
            pet.setStatus("Normal");
            System.out.println("Pet is fully rested and now awake.");
        }).start();
    }

    /**
     * Feed: Increases pet's fullness using the food item from pet.inv.
     * 
     * @param foodType the food item that the pet will consume
     */
    public void feedPet(String foodType) {
        if (!canUseCommand("Feed")) {
            System.out.println("Cannot feed pet in the current state: " + pet.getStatus());
            return;
        }
        arrayObject food = pet.inv.getItem(foodType);
        if (food == null) {
            System.out.println("No " + foodType + " left in inventory.");
            return;
        }
        int fullnessGain = food.getFullness();
        pet.inv.removeObj(foodType);
        int newFullness = Math.min(pet.getHunger() + fullnessGain, 100);
        pet.setHunger(newFullness);
        pet.setStatus("Fed, " + points);
        points = pet.getScore() + 5; // Award 5 points for feeding
        System.out.println("Fed pet with " + foodType + ". Fullness is now: " + newFullness + ". Points: " + points);

        // Push updated points to GameFile and StoreWindow if available.
        GameFile.updateScore(points);
        if (StoreWindow.currentStoreWindow != null) {
            StoreWindow.currentStoreWindow.updatePoints(points);
        }
    }

    /**
     * Give Gift: Increases the pet's happiness using a gift item from pet.inv.
     * 
     * @param giftType the gift type that the pet will receive
     */
    public void givePetPresent(String giftType) {
        if (!canUseCommand("Gift")) {
            System.out.println("Cannot give a gift in the current state: " + pet.getStatus());
            return;
        }
        arrayObject gift = pet.inv.getItem(giftType);
        if (gift == null) {
            System.out.println("No " + giftType + " left in inventory.");
            return;
        }
        int happinessGain = gift.getHappiness();
        pet.inv.removeObj(giftType);
        int newHappiness = Math.min(pet.getHappiness() + happinessGain, 100);
        pet.setHappiness(newHappiness);
        pet.setStatus("Received Gift");
        points = pet.getScore() + 10; // Award 10 points for giving gift.
        System.out.println("Gave pet a " + giftType + ". Happiness is now: " + newHappiness + ". Points: " + points);

        // Push updated points to GameFile and StoreWindow if available.
        GameFile.updateScore(points);
        if (StoreWindow.currentStoreWindow != null) {
            StoreWindow.currentStoreWindow.updatePoints(points);
        }
    }

    /**
     * Take to the Vet: Increases pet's health.
     * 
     * @param medicineType the type of medicine that the pet will receive while it is at the vet
     */
    public void visitVet(String medicineType) {
        if (!canUseCommand("Vet")) {
            System.out.println("Cannot visit vet in the current state: " + pet.getStatus());
            return;
        }
        arrayObject medicine = pet.inv.getItem(medicineType);
        if (medicine == null) {
            System.out.println("No " + medicineType + " left in inventory.");
            return;
        }
        pet.inv.removeObj(medicineType);
        long now = System.currentTimeMillis();
        if (now - lastVetTime < cooldownMillis) {
            System.out.println("Vet visit is on cooldown. Please wait.");
            return;
        }
        lastVetTime = now;
        int newHealth = Math.min(pet.getHealth() + 20, 100);
        pet.setHealth(newHealth);
        pet.setStatus("Visiting Vet");
        System.out.println("Pet visited the vet. Health is now: " + newHealth);

        // Deduct points for visiting the vet.
        points = Math.max(pet.getScore() + 5,0); // Deducts 5 points for visiting vet

        GameFile.updateScore(points);
        if (StoreWindow.currentStoreWindow != null) {
            StoreWindow.currentStoreWindow.updatePoints(points);
        }
        GameFile.updateScore(points);
        if (StoreWindow.currentStoreWindow != null) {
            StoreWindow.currentStoreWindow.updatePoints(points);
        }

        // Make pet angry for 3 seconds then revert to normal.
        pet.setStatus("Angry");
        javax.swing.Timer timer = new javax.swing.Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                pet.setStatus("Normal");
            }
        });
        timer.setRepeats(false); // Run only once
        timer.start();
    }

    /**
     * Play: Increases pet's happiness.
     */
    public void playWithPet() {
        if (!canUseCommand("Play")) {
            System.out.println("Cannot play in the current state: " + pet.getStatus());
            return;
        }
        long now = System.currentTimeMillis();
        if (now - lastPlayTime < cooldownMillis) {
            System.out.println("Play is on cooldown. Please wait.");
            return;
        }
        lastPlayTime = now;
        int newHappiness = Math.min(pet.getHappiness() + 15, 100);
        pet.setHappiness(newHappiness);
        pet.setStatus("Playing");
        points = pet.getScore() + 5; // Award 5 points for playing.
        System.out.println("Played with pet. Happiness is now: " + newHappiness + ". Points: " + points);

        // Push updated points to GameFile and StoreWindow if available.
        GameFile.updateScore(points);
        if (StoreWindow.currentStoreWindow != null) {
            StoreWindow.currentStoreWindow.updatePoints(points);
        }
    }

    /**
     * Exercise: Lowers pet's hunger/sleep while increasing health.
     */
    public void exercisepet() {
        if (!canUseCommand("Exercise")) {
            System.out.println("Cannot exercise in the current state: " + pet.getStatus());
            return;
        }
        int newHealth = Math.min(pet.getHealth() + 5, 100);
        int newHunger = Math.max(pet.getHunger() - 10, 0);
        int newSleepiness = Math.max(pet.getSleep() - 10, 0);
        pet.setHealth(newHealth);
        pet.setHunger(newHunger);
        pet.setSleep(newSleepiness);
        pet.setStatus("Exercising");

        points = pet.getScore() + 3; // Award 3 points for speaking.
        GameFile.updateScore(points);// push points
        if (StoreWindow.currentStoreWindow != null) {
            StoreWindow.currentStoreWindow.updatePoints(points);
        }

        System.out.println("Exercising: Health=" + newHealth + ", Fullness=" + newHunger + ", Sleepiness=" + newSleepiness + ". Points: " + points);

        // Push updated points to GameFile and StoreWindow if available.
        GameFile.updateScore(points);
        if (StoreWindow.currentStoreWindow != null) {
            StoreWindow.currentStoreWindow.updatePoints(points);
        }
    }

    /**
     * Plays a .wav file to indicate the pet is speaking.
     * 
     * @throws an error if the sound file cannot be played
     */
    public void makePetSpeak() {
        if (!canUseCommand("Speak")) {
            System.out.println("Cannot make pet speak in the current state: " + pet.getStatus());
            return;
        }
        pet.setStatus("Speaking");


        points = pet.getScore() + 10; // Award 10 points for speaking.
        GameFile.updateScore(points);// push points
        if (StoreWindow.currentStoreWindow != null) {
            StoreWindow.currentStoreWindow.updatePoints(points);
        }

        System.out.println("Pet is speaking...");

        // Choose the correct sound file based on the pet's type.
        String petType = pet.getType().toLowerCase();
        String soundFileName;
        System.out.println("type: " + petType);
        switch (petType) {
            case "bowser":
                soundFileName = "bowser laugh.wav";
                break;
            case "cat":
                soundFileName = "meow.wav";
                break;
            case "ryu":
                soundFileName = "shoryuken.wav";
                break;
            default:
                soundFileName = "bark.wav";
                break;
        }

        SoundPlayer p = new SoundPlayer();
        p.playLoop("wav sound files" + File.separator + soundFileName);

        

        
        javax.swing.Timer timer = new javax.swing.Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                pet.setStatus("Normal");
                p.stop();
            }
        });
        timer.setRepeats(false); // Run only once
        timer.start();
    }
}

