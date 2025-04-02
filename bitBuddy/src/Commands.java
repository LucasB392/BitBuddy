import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Commands {
    public Pet pet;
    private int points = 0; // New points variable for reward system

    // Cooldown trackers (in milliseconds)
    private long lastVetTime = 0;
    private long lastPlayTime = 0;
    private final long cooldownMillis = 30000; // 30 seconds cooldown

    // No longer using local food/gift inventories; we'll use pet.inv from ItemArray.
    // Maximum sleep value (when pet is fully rested)
    private final int MAX_SLEEP = 100;

    public Commands(Pet pet) {
        this.pet = pet;
        // Inventory setup is now done via pet.inv (presumably initialized in Pet)
    }

    // Getter for points
    public int getPoints() {
        return points;
    }

    // Check if a command can be used based on the pet's current state.
    // States follow precedence: Dead > Sleeping > Angry > Hungry > Normal.
    // If the pet is Angry, only Gift and Play are allowed.
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

    // a) Go to bed: Pet sleeps until its sleep value reaches the maximum.
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

    // b) Feed: Increases pet's fullness using the food item from pet.inv.
    public void feedPet(String foodType) {



        if (!canUseCommand("Feed")) {
            System.out.println("Cannot feed pet in the current state: " + pet.getStatus());
            return;
        }
        // Use the ItemArray from the Pet class.
        arrayObject food = pet.inv.getItem(foodType);
        if (food == null) {
            System.out.println("No " + foodType + " left in inventory.");
            return;
        }
        // Use the food's modFullness (assumed stored in getFullness()) as the fullness gain.
        int fullnessGain = food.getFullness();
        // Remove the food from the inventory.
        pet.inv.removeObj(foodType);
        // Increase pet's fullness (assuming higher value means less hungry).
        int newFullness = Math.min(pet.getHunger() + fullnessGain, 100);
        pet.setHunger(newFullness);
        pet.setStatus("Fed");
        points += 5; // Award 5 points for feeding.
        System.out.println("Fed pet with " + foodType + ". Fullness is now: " + newFullness + ". Points: " + points);
    }

    // c) Give Gift: Increases the pet's happiness using a gift item from pet.inv.
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
        // Remove the gift from inventory.
        pet.inv.removeObj(giftType);
        int newHappiness = Math.min(pet.getHappiness() + happinessGain, 100);
        pet.setHappiness(newHappiness);
        pet.setStatus("Received Gift");
        points += 10; // Award 10 points for giving a gift.
        System.out.println("Gave pet a " + giftType + ". Happiness is now: " + newHappiness + ". Points: " + points);
    }

    // d) Take to the Vet: Increases pet's health.
    public void visitVet() {
        if (!canUseCommand("Vet")) {
            System.out.println("Cannot visit vet in the current state: " + pet.getStatus());
            return;
        }
        long now = System.currentTimeMillis();
        if (now - lastVetTime < cooldownMillis) {
            System.out.println("Vet visit is on cooldown. Please wait.");
            return;
        }
        lastVetTime = now;
        int newHealth = Math.min(pet.getHealth() + 20, 100);
        pet.setHealth(newHealth);
        pet.setStatus("Visiting Vet");
        // Optionally subtract points if desired (e.g., points -= 5)
        System.out.println("Pet visited the vet. Health is now: " + newHealth);
    }

    // e) Play: Increases pet's happiness.
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
        points += 5; // Award 5 points for playing.
        System.out.println("Played with pet. Happiness is now: " + newHappiness + ". Points: " + points);
    }

    // f) Exercise: Lowers pet's hunger/sleep while increasing health.
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
        points += 3; // Award 3 points for exercising.
        System.out.println("Exercising: Health=" + newHealth + ", Fullness=" + newHunger + ", Sleepiness=" + newSleepiness + ". Points: " + points);
    }

    // Plays a .wav file to indicate the pet is speaking.
    public void makePetSpeak() {
        if (!canUseCommand("Speak")) {
            System.out.println("Cannot make pet speak in the current state: " + pet.getStatus());
            return;
        }
        pet.setStatus("Speaking");
        System.out.println("Pet is speaking...");

        try {
            File soundFile = new File("loon.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception e) {
            System.out.println("Error playing the sound file: " + e.getMessage());
            e.printStackTrace();
        }
        // After 2 seconds, revert the pet status back to Normal.
        new javax.swing.Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                pet.setStatus("Normal");
            }
        }).start();
    }

    
}
