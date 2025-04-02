import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ParentalControls extends JFrame {
    private JCheckBox musicToggle;

    public ParentalControls() {
        // Set up the frame
        setTitle("Parental Controls");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // Back Button
        JButton backButton = new JButton("\u2190 Back");
        backButton.setBounds(10, 10, 80, 30);
        backButton.addActionListener(e -> dispose());
        add(backButton);

        // Background Music Toggle
        musicToggle = new JCheckBox("Background Music");
        musicToggle.setBounds(350, 10, 140, 30);
        musicToggle.addActionListener(e -> toggleMusic());
        add(musicToggle);

        // Section Title
        JLabel titleLabel = new JLabel("Options:", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(150, 50, 200, 30);
        add(titleLabel);

        // View Parental Statistics Button
        JButton statsButton = new JButton("\uD83D\uDCCA View Parental Statistics");
        statsButton.setBounds(130, 100, 240, 40);
        statsButton.addActionListener(e -> openParentalStatistics());
        add(statsButton);

        JButton limitationsButton = new JButton("\u23F3 Set Parental Limitations");
        limitationsButton.setBounds(130, 150, 240, 40);
        limitationsButton.addActionListener(e -> openParentalLimitations());
        add(limitationsButton);

        JButton revivePetButton = new JButton("\uD83D\uDC80 Revive Pet");
        revivePetButton.setBounds(130, 200, 240, 40);
        // revivePetButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Reviving pet..."));
        revivePetButton.addActionListener(e -> revivePet());
        add(revivePetButton);

        JButton resetButton = new JButton("\u26A0 Reset Account");
        resetButton.setBounds(130, 250, 240, 40);
        resetButton.addActionListener(e -> resetAccount());
        add(resetButton);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to toggle background music
    private void toggleMusic() {
        if (musicToggle.isSelected()) {
            JOptionPane.showMessageDialog(this, "Music turned ON!");
        } else {
            JOptionPane.showMessageDialog(this, "Music turned OFF!");
        }
    }


    private void revivePet(){
        new reviveSelect();
    }


    
class reviveSelect extends JFrame {
    private int saveSlot = 0;
    public reviveSelect() {
        setTitle("Pet Revive");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Pick a Pet to Revive", SwingConstants.CENTER);
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
                    g.loadGame(saveSlot);
                    g.loadedPet.setHealth(100);
                    g.loadedPet.setHunger(100);
                    g.loadedPet.setHappiness(100);
                    g.loadedPet.setSleep(100);
                    g.saveGame(saveSlot,g.formatPetSave(g.loadedPet),g.loadedPet.inv.getSave());
                    dispose();
                });
            }else{
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // dispose();

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



    // Method to reset account
    private void resetAccount() {
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to reset the account? This cannot be undone!", 
                                                    "Confirm Reset", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {

            GameFile g = new GameFile();
            g.deleteSave(0);
            g.deleteSave(1);
            g.deleteSave(2);
            JOptionPane.showMessageDialog(this, "Account has been reset!");
        }
    }

    // Method to open Parental Statistics screen
    private void openParentalStatistics() {
        JFrame statsFrame = new JFrame("Parental Statistics");
        statsFrame.setSize(500, 400);
        statsFrame.setLayout(null);
        statsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Back Button
        JButton backButton = new JButton("\u2190 Back");
        backButton.setBounds(10, 10, 80, 30);
        backButton.addActionListener(e -> statsFrame.dispose());
        statsFrame.add(backButton);

        // Background Music Toggle
        JCheckBox musicToggle = new JCheckBox("Background Music");
        musicToggle.setBounds(350, 10, 140, 30);
        statsFrame.add(musicToggle);

        // Title
        JLabel titleLabel = new JLabel("Parental Statistics:", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(100, 50, 300, 30);
        statsFrame.add(titleLabel);

        // Total Play Time
        JLabel totalLabel = new JLabel("\uD83C\uDFAE Total play time:");
        totalLabel.setBounds(100, 100, 150, 30);
        statsFrame.add(totalLabel);
        String TtlPlayTime = "touch some grass";
        
        JTextField totalPlayTime = new JTextField(TtlPlayTime);
        totalPlayTime.setBounds(260, 100, 140, 30);
        totalPlayTime.setEditable(false);
        statsFrame.add(totalPlayTime);

        // Average Play Time
        JLabel averageLabel = new JLabel("\uD83D\uDCD5 Average play time:");
        averageLabel.setBounds(100, 150, 150, 30);
        statsFrame.add(averageLabel);
        String AvgPlayTime = "a bajillion";
        
        JTextField averagePlayTime = new JTextField(AvgPlayTime);
        averagePlayTime.setBounds(260, 150, 140, 30);
        averagePlayTime.setEditable(false);
        statsFrame.add(averagePlayTime);

        // Reset Button
        JButton resetButton = new JButton("Reset play time");
        resetButton.setBounds(180, 220, 140, 40);
        resetButton.addActionListener(e -> {
            totalPlayTime.setText("0s");
            averagePlayTime.setText("0s");
            JOptionPane.showMessageDialog(statsFrame, "Play time reset to 0s!");
        });
        statsFrame.add(resetButton);

        statsFrame.setLocationRelativeTo(null);
        statsFrame.setVisible(true);
    }
    private void openParentalLimitations() {
        JFrame limitationsFrame = new JFrame("Parental Limitations");
        limitationsFrame.setSize(500, 400);
        limitationsFrame.setLayout(null);
        limitationsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JButton backButton = new JButton("\u2190 Back");
        backButton.setBounds(10, 10, 80, 30);
        backButton.addActionListener(e -> limitationsFrame.dispose());
        limitationsFrame.add(backButton);

        JCheckBox musicToggle = new JCheckBox("Background Music");
        musicToggle.setBounds(350, 10, 140, 30);
        limitationsFrame.add(musicToggle);

        JLabel titleLabel = new JLabel("Parental Limitations:", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setBounds(100, 50, 300, 30);
        limitationsFrame.add(titleLabel);

        JLabel enableLabel = new JLabel("Enable Limitations:");
        enableLabel.setBounds(50, 100, 150, 30);
        limitationsFrame.add(enableLabel);

        JToggleButton enableLimitations = new JToggleButton("OFF");
        enableLimitations.setBounds(200, 100, 80, 30);
        enableLimitations.addActionListener(e -> {
            if (enableLimitations.isSelected()) {
                enableLimitations.setText("ON");
            } else {
                enableLimitations.setText("OFF");
            }
        });
        limitationsFrame.add(enableLimitations);

        JLabel fromLabel = new JLabel("From:");
        fromLabel.setBounds(120, 150, 50, 30);
        limitationsFrame.add(fromLabel);

        JTextField fromTimeField = new JTextField("08:00");
        fromTimeField.setBounds(170, 150, 60, 30);
        limitationsFrame.add(fromTimeField);

        JLabel toLabel = new JLabel("To:");
        toLabel.setBounds(250, 150, 50, 30);
        limitationsFrame.add(toLabel);

        JTextField toTimeField = new JTextField("22:00");
        toTimeField.setBounds(280, 150, 60, 30);
        limitationsFrame.add(toTimeField);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setBounds(190, 220, 100, 40);
        confirmButton.addActionListener(e -> {
            String fromTime = fromTimeField.getText();
            String toTime = toTimeField.getText();

            if (fromTime.compareTo(toTime) >= 0) {
                JOptionPane.showMessageDialog(limitationsFrame, "Invalid time range! 'From' time must be before 'To' time.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(limitationsFrame, "Parental limitations set from " + fromTime + " to " + toTime + ".");
            }
        });
        limitationsFrame.add(confirmButton);

        limitationsFrame.setLocationRelativeTo(null);
        limitationsFrame.setVisible(true);
    }

    // Method to show password prompt

    public static void showPasswordPrompt() {
        JDialog passwordDialog = new JDialog((Frame) null, "Enter Password", true);
        passwordDialog.setSize(300, 180);
        passwordDialog.setLayout(null);
        passwordDialog.setLocationRelativeTo(null);

        // Password field
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(20, 20, 250, 30);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(20, 60, 100, 30);
        loginButton.addActionListener(e -> {
            String password = new String(passwordField.getPassword());
            if (password.equals("admin123")) {  // < ---- Master Password
                passwordDialog.dispose();
                new ParentalControls();
            } else {
                JOptionPane.showMessageDialog(passwordDialog, "Incorrect password!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Forgot Password button
        JButton forgotButton = new JButton("Forgot Password?");
        forgotButton.setBounds(130, 60, 140, 30);
        forgotButton.addActionListener(e -> showForgotPasswordDialog());

        // Add components to password screen
        passwordDialog.add(passwordField);
        passwordDialog.add(loginButton);
        passwordDialog.add(forgotButton);
        passwordDialog.setVisible(true);
    }

    // Method to handle "Forgot Password?" functionality
    private static void showForgotPasswordDialog() {
        JOptionPane.showMessageDialog(null, "Hint: The password is 'admin123'", "Password Hint", JOptionPane.INFORMATION_MESSAGE);
    }

    // Main method to start the application with a password prompt
}
