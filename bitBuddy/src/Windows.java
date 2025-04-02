import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.File;


public class Windows {
    public int saveSlot;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu());
    }
}


class MainMenu extends JFrame {
    public MainMenu() {


        

        setTitle("Menu");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel label = new JLabel("BitBuddy", SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton startButton = new JButton("Choose Pet");
        JButton tutorialButton = new JButton("Tutorial");
        JButton parentalControlsButton = new JButton("Parental Controls");

        startButton.addActionListener(e -> {
            dispose(); // close this window
            new chooseSave(); // open game window
        });

        tutorialButton.addActionListener(e -> {
            dispose();
            new TutorialScreen();
        });
        parentalControlsButton.addActionListener(e -> {
            // dispose();

            ParentalControls.showPasswordPrompt();
        });
        buttonPanel.add(startButton);
        buttonPanel.add(tutorialButton);
        add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(parentalControlsButton);

        setVisible(true);
    }
}





class chooseSave extends JFrame {
    private int saveSlot = 0;
    public chooseSave() {
        setTitle("Pet Saves");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

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
            ImageIcon petIcon = new ImageIcon(g.loadedPet.getType()+ ".png");
            Image scaled = petIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(scaled));



            // Pet Type Label
            JLabel textLabel = new JLabel(isEmpty ? "" : g.loadedPet.getType(), SwingConstants.CENTER);
            textLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));




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

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }
}







class createPet extends JFrame {
    String saveType = "";
    String name = "";

    public createPet(int saveSlot) {
        setTitle("Choose your new Pet!");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());




        // Panel for buttons with images
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 20));

            // Load image
            ImageIcon icon1 = new ImageIcon("cat" + ".png");
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




            ImageIcon icon2 = new ImageIcon("bowser" + ".png");
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


            ImageIcon icon3 = new ImageIcon("ryu" + ".png");
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

        JTextField textField = new JTextField();
        textField.setMaximumSize(new Dimension(400, 30));
        textField.setAlignmentX(Component.CENTER_ALIGNMENT);

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



        inputPanel.add(textField);
        inputPanel.add(Box.createVerticalStrut(10));
        inputPanel.add(submitButton);

        // Add to frame
        add(buttonPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}

class ItemDetailWindow extends JFrame {
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


class InventoryWindow extends JFrame{
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

class StoreWindow extends JFrame {
    private int points;
    private JLabel pointsLabel;
    private ItemArray inventory;

    public StoreWindow(ItemArray inventory, int currentPoints) {
        this.inventory = inventory;
        this.points = currentPoints;

        setTitle("Store");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        pointsLabel = new JLabel("Points: " + points, SwingConstants.CENTER);
        pointsLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        add(pointsLabel, BorderLayout.NORTH);

        JPanel storePanel = new JPanel(new GridLayout(0, 3, 10, 10));
        JScrollPane scrollPane = new JScrollPane(storePanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Store items
        addStoreItem(storePanel, "Pizza", "Yummy food that fills you up.", "C:\\Users\\Auntic\\2212_Git_Repos\\group74\\group74\\GroupProject\\src\\Sprites\\pizza.png", 0, 0, 0, 25, 5, 20);
        addStoreItem(storePanel, "Potion", "Magical potion to restore health.", "C:\\Users\\Auntic\\2212_Git_Repos\\group74\\group74\\GroupProject\\src\\Sprites\\potion.png", 0, 30, 0, 0, 0, 30);
        addStoreItem(storePanel, "Bone", "A classic toy your pet loves.", "C:\\Users\\Auntic\\2212_Git_Repos\\group74\\group74\\GroupProject\\src\\Sprites\\bone.png", 1, 0, 0, 0, 20, 25);

        setVisible(true);
    }

    private void addStoreItem(JPanel panel, String name, String desc, String spritePath, int type, int health, int sleep, int fullness, int happiness, int cost) {
        File sprite = new File(spritePath);
        ImageIcon icon = null;
        if (sprite.exists()) {
            Image raw = new ImageIcon(sprite.getPath()).getImage();
            Image scaled = raw.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            icon = new ImageIcon(scaled);
        }

        JButton itemButton = new JButton(name, icon);
        itemButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        itemButton.setHorizontalTextPosition(SwingConstants.CENTER);

        itemButton.addActionListener(e -> {
            if (points < cost) {
                JOptionPane.showMessageDialog(this, "Not enough points!");
                return;
            }

            inventory.addObj(sprite, name, desc, type, health, sleep, fullness, happiness);
            points -= cost;
            GameFile.updateScore(points);
            pointsLabel.setText("Points: " + points);
            JOptionPane.showMessageDialog(this, name + " added to inventory!");
        });

        panel.add(itemButton);
    }
}


class GameWindow extends JFrame {
    public GameWindow(int save, long time) {
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
        JButton medicineButton = new JButton("Medicine");
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
            System.out.println("Inv size: " + g.loadedPet.inv.objectData.size());
            c.visitVet();
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
            c.givePetPresent("Ball");
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
            public void actionPerformed(ActionEvent evt) {
                petSprite.toggleFlip();
                petLabel.setIcon(new ImageIcon(petSprite.getDisplayedSprite()));
            }
        });
        flipTimer.start();
    }

    private JProgressBar createProgressBar(int value) {
        JProgressBar bar = new JProgressBar(0, 100);
        bar.setValue(value);
        return bar;
    }

    private JPanel createStatusPanel(String label, JProgressBar bar) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel textLabel = new JLabel(label);
        panel.add(textLabel, BorderLayout.WEST);
        panel.add(bar, BorderLayout.CENTER);
        return panel;
    }
}





