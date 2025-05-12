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
 * @author Elmar
 */

public class PetSprite {
	//* Private variable for he full sprite sheet image */
    private BufferedImage spriteSheet;
    //* Private variable for the currently displayed sprite */
    private BufferedImage currentSprite;
    //* Private variable for either"bowser", "cat", "dog", or "ryu" */
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
                filePath = "Sprites" + File.separator + "Cat Sprite.png";
                break;
            case "dog":
                filePath = "Sprites" + File.separator + "Dog Sprite.png";
                break;
            case "ryu":
                filePath = "Sprites" + File.separator + "Ryu Sprite.png";
                break;
            default:
                filePath = "Sprites" + File.separator + "Bowser Sprite.png";
                break;
        }
        try {
            spriteSheet = ImageIO.read(new File(filePath));
            // Process background transparency for pet types that need it.
            Color bgColor = null;
            if (this.petType.equals("cat")) {
                bgColor = new Color(255, 0, 255, 255);
            } else if (this.petType.equals("dog")) {
                bgColor = new Color(0, 248, 248, 255);
            } else if (this.petType.equals("ryu")) {
                bgColor = new Color(0, 255, 80, 255);
            }
            if (bgColor != null) {
                spriteSheet = ImageUtils.makeColorTransparent(spriteSheet, bgColor);
            }
        } catch (Exception e) {
            System.out.println("Error loading sprite sheet for " + petType + ": " + e.getMessage());
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
        int x = 0, y = 0, w = 0, h = 0;
        String state = currentState.toLowerCase();
        switch (petType) {
            case "bowser":
                switch (state) {
                    case "normal":
                        x = 2; y = 28; w = 68; h = 72;
                        break;
                    case "sleeping":
                        x = 2; y = 1303; w = 90; h = 56;
                        break;
                    case "angry":
                        x = 2; y = 1960; w = 72; h = 79;
                        break;
                    case "hungry":
                        x = 2; y = 4081; w = 71; h = 82;
                        break;
                    case "speaking":
                        x = 572; y = 4264; w = 73; h = 78;
                        break;
                    case "dead":
                        x = 2; y = 4356; w = 68; h = 73;
                        break;
                    default:
                        x = 2; y = 28; w = 68; h = 72;
                        break;
                }
                break;
            case "cat":
                switch (state) {
                    case "normal":
                        x = 12; y = 8; w = 41; h = 34;
                        break;
                    case "sleeping":
                        x = 133; y = 64; w = 49; h = 32;
                        break;
                    case "hungry":
                        x = 193; y = 63; w = 49; h = 32;
                        break;
                    case "angry":
                        x = 62; y = 0; w = 37; h = 43;
                        break;
                    case "speaking":
                        x = 109; y = 3; w = 38; h = 39;
                        break;
                    case "dead":
                        x = 255; y = 58; w = 29; h = 56;
                        break;
                    default:
                        x = 12; y = 8; w = 41; h = 34;
                        break;
                }
                break;
            case "dog":
                switch (state) {
                    case "normal":
                        x = 6; y = 54; w = 81; h = 85;
                        break;
                    case "sleeping":
                        x = 206; y = 240; w = 55; h = 46;
                        break;
                    case "angry":
                        x = 136; y = 313; w = 48; h = 50;
                        break;
                    case "hungry":
                        x = 396; y = 47; w = 83; h = 92;
                        break;
                    case "speaking":
                        x = 73; y = 244; w = 53; h = 53;
                        break;
                    case "dead":
                        x = 266; y = 565; w = 64; h = 64;
                        break;
                    default:
                        x = 6; y = 54; w = 81; h = 85;
                        break;
                }
                break;
            case "ryu":
                switch (state) {
                    case "normal":
                        x = 22; y = 115; w = 75; h = 96;
                        break;
                    case "sleeping":
                        x = 4; y = 1815; w = 88; h = 56;
                        break;
                    case "angry":
                        x = 340; y = 116; w = 69; h = 95;
                        break;
                    case "hungry":
                        x = 223; y = 1320; w = 65; h = 72;
                        break;
                    case "speaking":
                        x = 1178; y = 118; w = 78; h = 91;
                        break;
                    case "dead":
                        x = 1329; y = 1204; w = 98; h = 56;
                        break;
                    default:
                        x = 22; y = 115; w = 75; h = 96;
                        break;
                }
                break;
            default:
                // Fallback to Bowser's normal state.
                x = 2; y = 28; w = 68; h = 72;
                break;
        }
        try {
            currentSprite = spriteSheet.getSubimage(x, y, w, h);
            System.out.println("Sprite updated: petType=" + petType + ", state=" + currentState);
        } catch (Exception e) {
            System.out.println("Error extracting sprite for pet type " + petType + " and state " + currentState + ": " + e.getMessage());
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
        if (!flipped) {
            return currentSprite;
        } else {
            int width = currentSprite.getWidth();
            int height = currentSprite.getHeight();
            BufferedImage flippedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = flippedImage.createGraphics();
            // Draw the image flipped horizontally.
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



