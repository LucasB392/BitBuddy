import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Class handling pet sprite images and animations
 * <br><br>
 * This class opens the sprites and displays them on the screen. Additionally, the pet's states will be displayed. The selected pet will be displayed during gameplay.<br><br>
 * 
 * @version 1.0
 * @author Lucas Brown
 */

public class PetSprite {
    //* Private variable for the currently displayed sprite */
    private BufferedImage currentSprite;
    //* Private variable for either "bowser", "cat", "dog", or "ryu" */
    private String petType;
    //* Private variable for states e.g., "normal", "sleeping", "angry", "hungry", "speaking", "dead" */
    private String currentState;
    //* Private variable for flag for horizontal flip */
    private boolean flipped = false;

    /**
     * Constructor: given the pet type, load the correct sprite sheet.
     * 
     * @param petType defines the type of pet
     * @throw an exception if the pet sprite cannot be displayed on the screen
     */
    public PetSprite(String petType) {
        this.petType = petType.toLowerCase();

        String filePath = "";
        switch (this.petType) {
            case "bowser":
                filePath = "Sprites" + File.separator + "Bowser Sprite.png";
                break;
            case "cat":
                filePath = "Sprites" + File.separator + "cat.png";
                break;
            case "ryu":
                filePath = "Sprites" + File.separator + "ryu.png";
                break;
            default:
                filePath = "Sprites" + File.separator + "cat.png";
                break;
        }
        try {
            // Process background transparency for pet types that need it.
            Color bgColor = null;
            if (this.petType.equals("cat")) {
                bgColor = new Color(255, 0, 255, 255);
            } else if (this.petType.equals("ryu")) {
                bgColor = new Color(0, 255, 80, 255);
            }
        } catch (Exception e) {
            System.out.println("Error making background transparent for " + petType + ": " + e.getMessage());
            e.printStackTrace();
        }
        // Set default state to normal.
        setStatus("normal");
    }

    /**
     * Sets the current state (e.g., "normal", "sleeping", etc.) and updates the sprite.
     * 
     * @param status the current state of the pet
     */
    public void setStatus(String status) {
        currentState = status;
        updateSprite();
    }

    /**
     * Updates the currentSprite using the correct coordinates/dimensions for the pet type and state.
     * 
     * @throw an exception if the sprite cannot be updated to reflect its current state
     */
    private void updateSprite() {

        // e.g. Sprites/ryuSprites/ryuHUNGRY.png
        String spriteFilePath = "out" + File.separator + "Sprites" + File.separator + petType.toLowerCase() + "Sprites" + 
        File.separator + petType.toLowerCase() + currentState.toUpperCase() + ".png";
                    
        try {
            File spriteFile = new File(spriteFilePath);
            if (!spriteFile.exists()) {
                System.out.println(this.currentState);
                System.err.println("Sprite not found: " + spriteFile.getAbsolutePath());
                return;
            }

            currentSprite = ImageIO.read(spriteFile);
            System.out.println("Sprite updated: petType=" + petType + ", state=" + currentState);
        } catch (Exception e) {
            System.out.println("Error loading sprite: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Toggles the flipped flag.
     */
    public void toggleFlip() {
        flipped = !flipped;
    }

    /**
     * Returns the sprite image that should be displayed (flipped if needed).
     * 
     * @return the displayed sprite on the screen, can be flipped horizontally
     */
    public BufferedImage getDisplayedSprite() {
        if (currentSprite == null) return null;

        if (!flipped) {
            return currentSprite;
        } else {
            int width = currentSprite.getWidth();
            int height = currentSprite.getHeight();
            BufferedImage flippedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = flippedImage.createGraphics();
            g.drawImage(currentSprite, 0, 0, width, height, width, 0, 0, height, null);
            g.dispose();
            return flippedImage;
        }
    }

    /**
     * Returns the current (non-flipped) sprite image.
     * @return the current sprite
     */
    public BufferedImage getCurrentSprite() {
        return currentSprite;
    }
}



