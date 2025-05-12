import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Class handling the tutorial for the game
 * <br><br>
 * This class opens the tutorial screen, displays images that depict other screens in the game, and lists instructions on how to play the game.<br><br>
 * 
 * @version 1.0
 * @author Matthew
 * @author Scott
 */

public class TutorialScreen extends JFrame {
    //* private variable that states what step of the tutorial the player is on */
	private int step = 0;

    // Editable tutorial captions
	/* array of tutorial captions */
	private final String[] tutorialCaptions = {
		"Select 'Choose Pet' to start the game", "Select the file you wish to use.", "Here is your pet, including statistics and commands.", "Here is your inventory.", "Select an item to give your pet.", "Parents: Select a pet to revive.", "Parents: View the play time statistics of the player.", "Parents: This is the parental controls screen.", "Parents: Enter the correct password to view the parental controls screen.", "Parents: Set the time when the player is allowed to play the game.","Parents: Resetting the account will erase your progress, be careful!"
};

	/* private label for images */
    private final JLabel imageLabel;
    /* private label for captions */
    private final JLabel captionLabel;
    /* private variable that represents the next button */
    private final JButton nextButton;

    /*
     * The tutorial window displays the tutorial screen as well as the images and captions according to the current step
     */
    public TutorialScreen() {
        setTitle("BitBuddy's Tutorial Screen");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        // Image display
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(imageLabel, BorderLayout.CENTER);

        // Caption label
        captionLabel = new JLabel(tutorialCaptions[step], SwingConstants.CENTER);
        captionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(captionLabel, BorderLayout.NORTH);

        // Next button
        nextButton = new JButton("Next");
        nextButton.addActionListener(new NextButtonListener());
        add(nextButton, BorderLayout.SOUTH);

        // Load first image
        updateScreen();

        setVisible(true);
    }

    /**
     * Update window display
     */
    private void updateScreen() {
        ImageIcon icon = new ImageIcon("tutpics/tut" + (step + 1) + ".png");
        Image image = icon.getImage().getScaledInstance(700, 400, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(image));
        captionLabel.setText(tutorialCaptions[step]);
    }

    /**
     * Buttons to move forward in tutorial
     * <br><br>
     * This private class will call to update the screen when the player clicks on the "next" button.<br><br>
     */
    private class NextButtonListener implements ActionListener {
        @Override
        /**
         * Update the steps.
         * Close window once all steps have been iterated through.
         */
        public void actionPerformed(ActionEvent e) {
            step++;
            if (step < tutorialCaptions.length) {
                updateScreen();
            } else {
                dispose(); // close window
            }
        }
    }
}

