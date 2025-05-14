import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

/**
 * Class that is responsible for opening the shop and displaying items for sale
 * <br><br>
 * This class opens the store window and displays the items for sale (bone, pizza, potion). The player must have sufficient points to purchase an item.<br><br>
 * 
 * @version 1.0
 * @author Lucas Brown
 */



class StoreWindow extends JFrame {

    /** Static reference to the currently open StoreWindow. */
    public static StoreWindow currentStoreWindow;

    /** private variable to store player's points */
    private int points;
    /** private label to name the store window */
    private JLabel pointsLabel;
    /** private inventory of the player */
    private ItemArray inventory;
    
    /**
     * Open the store window and display the items for sale.
     * 
     * @param inventory represents the inventory of the player
     * @param currentPoints represent the points the player has
     */
    public StoreWindow(ItemArray inventory, int currentPoints) {
        this.inventory = inventory;
        this.points = currentPoints;

        // Set this instance as the current store window.
        currentStoreWindow = this;

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
        addStoreItem(storePanel, "Pizza", "Yummy food that fills you up.",
                "pizza.png",
                0, 0, 0, 25, 5, 20);
        addStoreItem(storePanel, "Potion", "Magical potion to restore health.",
                "potion.png",
                0, 30, 0, 0, 0, 30);
        addStoreItem(storePanel, "Bone", "A classic toy your pet loves.",
                "bone.png",
                1, 0, 0, 0, 20, 25);

        setVisible(true);
    }

    /**
     * Method to update the points and refresh the UI.
     * 
     * @param newPoints the new amount of points to replace the existing amount
     */
    public void updatePoints(int newPoints) {
        this.points = newPoints;
        pointsLabel.setText("Points: " + newPoints);
    }

    /**
     * Method to add the store item to the shop and display the item.
     * 
     * @param panel the screen for the store window
     * @param name the name of the item
     * @param desc the description of the item
     * @param spritePath the image file of the item
     * @param type the type of item object
     * @param health the health contribution of the item
     * @param sleep the sleep contribution of the item
     * @param fullness the fullness contribution of the item
     * @param happiness the happiness contribution of the item
     * @param cost the cost to purchase the item
     */
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
            updatePoints(points);
            JOptionPane.showMessageDialog(this, name + " added to inventory!");
        });

        panel.add(itemButton);
    }
}

