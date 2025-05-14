import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class that displays the window
 * <br><br>
 * This is the main class that is responsible for displaying the main menu, displaying other screens, and creating pets.<br><br>
 * 
 * @version 1.0
 * @author Lucas
 */

public class Windows {
	/** The private variable that represents the save slot */
    public int saveSlot;

    /**
     * Main method that will open the game.
     * 
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu());
    }

    /**
     * Method to load the parental controls specified by the parent.
     * 
     * @throws an IOException if the .csv file cannot be found
     */
    public static void loadParentalControls() {
        try (BufferedReader br = new BufferedReader(new FileReader("ParentalControls.csv"))) {
            String line = br.readLine();
            if (line != null && line.matches("[TF]\\d{2}:\\d{2}/\\d{2}:\\d{2}")) {
                boolean limit = line.charAt(0) == 'T';
    
                String[] times = line.substring(1).split("/");
                String[] startParts = times[0].split(":");
                String[] endParts = times[1].split(":");
    
                int startMin = Integer.parseInt(startParts[0]) * 60 + Integer.parseInt(startParts[1]);
                int endMin = Integer.parseInt(endParts[0]) * 60 + Integer.parseInt(endParts[1]);
    
                // Use the parsed values
                System.out.println("Limitations: " + limit);
                System.out.println("Start (min): " + startMin);
                System.out.println("End (min): " + endMin);
    
                int currentMin = java.time.LocalTime.now().getHour() * 60 + java.time.LocalTime.now().getMinute();
                if(limit){
                    if(currentMin < startMin || currentMin > endMin){
                        System.exit(0);
                    }
                }
            } else {
                System.out.println("Invalid format in ParentalControls.csv");
            }
        } catch (IOException e) {
            System.out.println("ParentalControls.csv not found or unreadable.");
        }
    }
}

/**
 * Open the main menu
 * <br><br>
 * This class opens the main menu and displays the required buttons so that the player can proceed to another screen.<br><br>
 */

class MainMenu extends JFrame {
	
	/**
	 * Open the main menu screen.
	 * Display buttons.
	 */

    public MainMenu() {

        setTitle("Menu");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    
        String logoPath = "Sprites" + File.separator +"logo.png";
    
        // Load the image
        ImageIcon logoIcon = new ImageIcon(getClass().getResource("Sprites" + File.separator + "logo.png"));

        
        JLabel logoLabel = new JLabel(logoIcon, SwingConstants.CENTER);
    
        // Create a panel for the logo and group info
        JPanel logoPanel = new JPanel();
        logoPanel.setLayout(new BorderLayout());
    
        logoPanel.add(logoLabel, BorderLayout.CENTER);
    
        // Create group info label
        String groupText = "<html><div style='text-align: center;'>"
                + "Lucas Brown "
                + "</div></html>";
        JLabel groupInfo = new JLabel(groupText, SwingConstants.CENTER);
    
        logoPanel.add(groupInfo, BorderLayout.SOUTH);
    
        add(logoPanel, BorderLayout.CENTER);
    
        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Choose Pet");
        JButton tutorialButton = new JButton("Tutorial");
        JButton parentalControlsButton = new JButton("Parental Controls");
    
        startButton.addActionListener(e -> {
            dispose(); // close this window
            new chooseSave(); // open game window
        });
    
        tutorialButton.addActionListener(e -> {
            new TutorialScreen();
        });
    
        parentalControlsButton.addActionListener(e -> {
            ParentalControls.showPasswordPrompt();
        });
    
        buttonPanel.add(startButton);
        buttonPanel.add(tutorialButton);
        buttonPanel.add(parentalControlsButton);
    
        add(buttonPanel, BorderLayout.SOUTH);
    
        setVisible(true);
    }
    
}

/**
 * Select the save file
 * <br><br>
 * This class opens the save screen and allows the player to select a save slot.<br><br>
 */

class chooseSave extends JFrame {
	/** The private variable that represents the save slot */
    private int saveSlot = 0;
    
    /**
     * Method to open the save screen.
     * The player can select a save slot.
     * Pets are displayed as sprites.
     */
    public chooseSave() {
        setTitle("Pet Saves");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        System.out.println("HIIII");

        // Title
        JLabel titleLabel = new JLabel("Pick a Pet to Play with!", SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Panel to hold all 3 slot panels side by side
        JPanel mainPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        GameFile g = new GameFile();

        for (int i = 0; i < 3; i++) {
            final int saveSlot = i;
            g.loadGame(i);
            String name = g.loadedPet.getName();
            boolean isEmpty = g.loadedPet.firstSave;

            JButton button = new JButton(name);
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.setPreferredSize(new Dimension(100, 40));

            if (!isEmpty) {
                button.addActionListener(e -> {
                    dispose();
                    
                    new GameWindow(saveSlot,System.currentTimeMillis());
                });
            }else{
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                        new createPet(saveSlot);

                    }
                });
            }

            // Pet Image
            JLabel imageLabel = new JLabel();
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

            ImageIcon petIcon = new ImageIcon("Sprites" + File.separator + g.loadedPet.getType()+ ".png");
            Image scaled = petIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            

            imageLabel.setIcon(new ImageIcon(scaled));

            // Pet Type Label
            String typeText = isEmpty ? "" : g.loadedPet.getType().toUpperCase();
            JLabel textLabel = new JLabel(typeText, SwingConstants.CENTER);
            textLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
            textLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
            textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Vertical layout for one slot
            JPanel slotPanel = new JPanel();
            slotPanel.setLayout(new BoxLayout(slotPanel, BoxLayout.Y_AXIS));
            slotPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
            slotPanel.add(button);
            slotPanel.add(Box.createVerticalStrut(10));
            slotPanel.add(imageLabel);
            slotPanel.add(Box.createVerticalStrut(5));
            slotPanel.add(textLabel);

            mainPanel.add(slotPanel);
        }
        
        
        
        System.out.println("TESTING SPOT A");



        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}

/**
 * Create a pet
 * <br><br>
 * This class allows the player to select a pet when they are starting the game.<br><br>
 */

class createPet extends JFrame {
	/** empty string defining the pet's type */
    String saveType = "";
    /** empty string defining the pet's name */
    String name = "";

    /**
     * Method to create a pet.
     * The player will select a pet.
     * Pets are displayed as sprites.
     * 
     * @param saveSlot representing the save slot the player is using
     */
    public createPet(int saveSlot) {
        setTitle("Choose your new Pet!");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for buttons with images
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));

            // Load image
            ImageIcon icon1 = new ImageIcon("Sprites" + File.separator +"cat" + ".png");
            Image scaled1 = icon1.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon1 = new ImageIcon(scaled1);

            JButton btn1 = new JButton("Cat", scaledIcon1);
            btn1.setVerticalTextPosition(SwingConstants.BOTTOM);
            btn1.setHorizontalTextPosition(SwingConstants.CENTER);
            btn1.setPreferredSize(new Dimension(100, 100));
            buttonPanel.add(btn1);

            btn1.addActionListener(e -> {
                saveType = "cat";
            });

            ImageIcon icon2 = new ImageIcon("Sprites" + File.separator +"bowser" + ".png");
            Image scaled2 = icon2.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon2 = new ImageIcon(scaled2);

            JButton btn2 = new JButton("Bowser ", scaledIcon2);
            btn2.setVerticalTextPosition(SwingConstants.BOTTOM);
            btn2.setHorizontalTextPosition(SwingConstants.CENTER);
            btn2.setPreferredSize(new Dimension(100, 100));
            buttonPanel.add(btn2);

            btn2.addActionListener(e -> {
                saveType = "bowser";
            });


            ImageIcon icon3 = new ImageIcon("Sprites" + File.separator + "ryu" + ".png");
            Image scaled3 = icon3.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon3 = new ImageIcon(scaled3);

            JButton btn3 = new JButton("Ryu", scaledIcon3);
            btn3.setVerticalTextPosition(SwingConstants.BOTTOM);
            btn3.setHorizontalTextPosition(SwingConstants.CENTER);
            btn3.setPreferredSize(new Dimension(100, 100));
            buttonPanel.add(btn3);

            btn3.addActionListener(e -> {
                saveType = "ryu";
            });

        // Text field + submit button panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50));

        JPanel nameFieldPanel = new JPanel();
nameFieldPanel.setLayout(new BoxLayout(nameFieldPanel, BoxLayout.X_AXIS));
nameFieldPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

JLabel nameLabel = new JLabel("Name:");
nameLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
nameLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

JTextField textField = new JTextField();
textField.setMaximumSize(new Dimension(300, 30));

nameFieldPanel.add(nameLabel);
nameFieldPanel.add(textField);

JButton submitButton = new JButton("Submit");
submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);


        submitButton.addActionListener(e -> {
            String userInput = textField.getText();
            if (saveType.equals("") || userInput.equals("")) {
                System.out.println("Please Select and Name your Pet!");
            }else{
                Pet newPetSave = new Pet(userInput,saveType);
                GameFile g = new GameFile();
                g.saveGame(saveSlot,g.formatPetSave(newPetSave),g.loadedPet.inv.getSave());
                new GameWindow(saveSlot,System.currentTimeMillis());
            }
        });

        inputPanel.add(nameFieldPanel);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(submitButton);
        
        inputPanel.add(textField);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(submitButton);

        // Add to frame
        add(buttonPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}

/**
 * Describe the items
 * <br><br>
 * This class opens a screen which lists the items in the array. The items contain their respective descriptions.<br><br>
 */
class ItemDetailWindow extends JFrame {
	
	/**
	 * Method to display the items in the window.
	 * 
	 * @param the selected item to be displayed
	 */
    public ItemDetailWindow(arrayObject item) {
        setTitle(item.getName());
        setSize(300, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JLabel nameLabel = new JLabel(item.getName(), SwingConstants.CENTER);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        add(nameLabel, BorderLayout.NORTH);

        JTextArea desc = new JTextArea();
        desc.setEditable(false);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setText(
            "Description: " + item.getDescription() + "\n\n" +
            "Type: " + item.getType() + "\n" +
            "Health: " + item.getHealth() + "\n" +
            "Sleep: " + item.getSleep() + "\n" +
            "Fullness: " + item.getFullness() + "\n" +
            "Happiness: " + item.getHappiness()
        );
        add(desc, BorderLayout.CENTER);

        setVisible(true);
    }
}

/**
 *  Describe items in inventory
 *  <br><br>
 *  This class opens the inventory screen and displays the items that the player contains in their inventory.<br><br>
 */
class InventoryWindow extends JFrame{
	
	/**
	 * Open the inventory window and displays the listed items in an array.
	 * 
	 * @param inventory represents the player's inventory
	 */
    public InventoryWindow(ItemArray inventory){
        setTitle("Inventory");
        setSize(600,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel gridPanel = new JPanel(new GridLayout(0, 4, 10, 10)); // 4 columns, dynamic rows
        JScrollPane scrollPane = new JScrollPane(gridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane);

        for (int i = 0; i < inventory.getSize(); i++) {
            arrayObject item = inventory.objectData.get(i);

            ImageIcon icon = null;
            if (item.getSprite().exists()) {
                Image raw = new ImageIcon(item.getSprite().getPath()).getImage();
                Image scaled = raw.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
                icon = new ImageIcon(scaled);
            }

            JButton itemButton = new JButton(item.getName(), icon);
            itemButton.setVerticalTextPosition(SwingConstants.BOTTOM);
            itemButton.setHorizontalTextPosition(SwingConstants.CENTER);
            itemButton.addActionListener(e -> {
                new ItemDetailWindow(item);
            });

            gridPanel.add(itemButton);
        }
        setVisible(true);
    }
}


/**
 * Open the gameplay window that displays the current pet.
 * <br><br>
 * This class opens the gameplay window where they will see their selected pet. Vital statistics bars are displayed as well as additional buttons that allow the player to go to the store or their inventory.<br><br>
 */
class GameWindow extends JFrame {
	
	/**
	 * Method to open the game window.
	 * The pet is displayed.
	 * The player will be able to view the vital statistics of the pet.
	 * The player can visit the store and their inventory
	 * The player can save the game at any time.
	 * 
	 * @param save the integer that represents the save slot
	 * @param time the time spent playing on the selected save slot
	 */
    public GameWindow(int save, long time) {
        Windows.loadParentalControls();
        setTitle("Game Page");
        setSize(600, 400);
        System.out.println("time: " + time);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        GameFile g = new GameFile();
        g.loadGame(save);
        
        g.loadedPet.setCounter(System.currentTimeMillis());

        Commands c = new Commands(g.loadedPet);
        
        JPanel itemPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        // Top panel with title and save button, and inventory button

        
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(c.pet.getName(), SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));




        topPanel.add(titleLabel, BorderLayout.CENTER);
        JButton saveButton = new JButton("Save");
        JButton inventoryButton = new JButton("Inventory");
        JButton storeButton = new JButton("Store");
        saveButton.setMargin(new Insets(2, 8, 2, 8)); // small button padding
        storeButton.setMargin(new Insets(2, 8, 2, 8)); // small button padding
        inventoryButton.setMargin(new Insets(2, 8, 2, 8)); // small button padding
        saveButton.setFont(new Font("Arial", Font.PLAIN, 12));
        storeButton.setFont(new Font("Arial", Font.PLAIN, 12));
        inventoryButton.setFont(new Font("Arial", Font.PLAIN, 12));
        saveButton.addActionListener(e -> {
            g.saveGame(save, g.formatPetSave(g.loadedPet), g.loadedInventory.getSave());
            JOptionPane.showMessageDialog(this, "Game saved!");
        });
        inventoryButton.addActionListener(e -> {
            new InventoryWindow(g.loadedInventory);
        });
        storeButton.addActionListener(e -> {
            new StoreWindow(g.loadedInventory, g.getScore());
        });
        topPanel.add(saveButton, BorderLayout.EAST);
        itemPanel.add(inventoryButton);
        itemPanel.add(storeButton);
        add(itemPanel, BorderLayout.WEST);
        add(topPanel, BorderLayout.NORTH);

        // Center panel with pet image and health bars
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));

        // Left: Pet image using sprite sheet
        String petType = c.pet.getType(); // e.g., "bowser"
        PetSprite petSprite = new PetSprite(petType);

        // Create the pet label using the current sprite from PetSprite
        JLabel petLabel = new JLabel();
        petLabel.setHorizontalAlignment(SwingConstants.CENTER);
        petLabel.setIcon(new ImageIcon(petSprite.getDisplayedSprite()));
        centerPanel.add(petLabel);

        

        // Right: Health bars
        JPanel statsPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        JProgressBar healthBar = createProgressBar(c.pet.getHealth());
        JProgressBar sleepBar = createProgressBar(c.pet.getSleep());
        JProgressBar hungerBar = createProgressBar(c.pet.getHunger());
        JProgressBar happinessBar = createProgressBar(c.pet.getHappiness());
        statsPanel.add(createStatusPanel("Health", healthBar));
        statsPanel.add(createStatusPanel("Sleep", sleepBar));
        statsPanel.add(createStatusPanel("Hunger", hungerBar));
        statsPanel.add(createStatusPanel("Happiness", happinessBar));
        centerPanel.add(statsPanel);
        add(centerPanel, BorderLayout.CENTER);

        // Bottom panel with control buttons (unchanged)
        JPanel buttonPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        JButton feedButton = new JButton("Feed");
        JButton sleepButton = new JButton("Sleep");
        JButton playButton = new JButton("Play");
        JButton medicineButton = new JButton("Visit Vet");
        JButton trainButton = new JButton("Train");
        JButton exerciseButton = new JButton("Exercise");
        JButton moveButton = new JButton("Move");
        JButton speakButton = new JButton("Speak");
        JButton presentButton = new JButton("Give Present");

        feedButton.addActionListener(e -> {
            c.feedPet("Pizza");
            hungerBar.setValue(c.pet.getHunger());
        });
        sleepButton.addActionListener(e -> {
            c.sleepyPet();
            sleepBar.setValue(c.pet.getSleep());
        });
        playButton.addActionListener(e -> {
            c.playWithPet();
            happinessBar.setValue(c.pet.getHappiness());
        });
        medicineButton.addActionListener(e -> {
            c.visitVet("Potion");
            healthBar.setValue(c.pet.getHealth());
        });
        exerciseButton.addActionListener(e -> {
            c.exercisepet();
            healthBar.setValue(c.pet.getHealth());
            hungerBar.setValue(c.pet.getHunger());
        });
        speakButton.addActionListener(e -> {
            c.makePetSpeak();
            // Optionally, update pet status here if needed.
        });
        presentButton.addActionListener(e -> {
            c.givePetPresent("Bone");
        });
        buttonPanel.add(feedButton);
        buttonPanel.add(sleepButton);
        buttonPanel.add(playButton);
        buttonPanel.add(medicineButton);
        buttonPanel.add(trainButton);
        buttonPanel.add(exerciseButton);
        buttonPanel.add(moveButton);
        buttonPanel.add(speakButton);
        buttonPanel.add(presentButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

        // Timer to update pet stats and sprite every second.
        Timer timer = new Timer(1000, new ActionListener() {
        	
        	/**
        	 * Method to update the vital statistics of the pet over time
        	 * 
        	 * @param evt an event that will affect the vital statistics of the pet
        	 */
            public void actionPerformed(ActionEvent evt) {
                g.loadedPet.petCounter(System.currentTimeMillis());
                hungerBar.setValue(c.pet.getHunger());
                sleepBar.setValue(c.pet.getSleep());
                healthBar.setValue(c.pet.getHealth());
                happinessBar.setValue(c.pet.getHappiness());

                // Update the pet sprite based on the pet's current status.
                petSprite.setStatus(c.pet.getStatus());
                petLabel.setIcon(new ImageIcon(petSprite.getDisplayedSprite()));
            }
        });
        timer.start();

        // Additional Timer to flip the sprite horizontally every 5 seconds.
        Timer flipTimer = new Timer(5000, new ActionListener() {
        	
        	/**
        	 * Method to display a horizontally flipped sprite every 5 seconds.
        	 */
            public void actionPerformed(ActionEvent evt) {
                petSprite.toggleFlip();
                petLabel.setIcon(new ImageIcon(petSprite.getDisplayedSprite()));
            }
        });
        flipTimer.start();


        
    }

    /**
     * Method to create the vital statistics bars
     * 
     * @param value the integer value of the statistic
     * @return a bar containing the value
     */
    private JProgressBar createProgressBar(int value) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(value);
        return bar;
    }

    /**
     * Method to create a status panel displaying the vital statistics bars
     * 
     * @param label the name of the bar
     * @param bar a bar containing the value
     * @return the panel containing the vital statistics bars
     */
    private JPanel createStatusPanel(String label, JProgressBar bar) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel textLabel = new JLabel(label);
        panel.add(textLabel, BorderLayout.WEST);
        panel.add(bar, BorderLayout.CENTER);
        return panel;
    }
}

