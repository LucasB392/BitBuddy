import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;



public class TutorialScreen extends JFrame {

    public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new TutorialScreen());
	}



    private int step = 0;

    // Editable tutorial captions
    private final String[] tutorialCaptions = {
            "Welcome to BitBuddy!", "Look after your own virtual pet.", "Train your pet's vocal cords and let it sing.",
            "Let your pet sleep so that it can regain energy.", "Feed your pet nutritious food to keep it healthy.",
            "Keep your pet happy by giving them gifts.", "Take your pet to the vet when it gets sick.",
            "Play with the pet to keep it entertained.", "Take your pet out on a walk and burn some calories.",
            "Don't let your pet die.", "Good luck! You'll need it."
    };

    private final JLabel imageLabel;
    private final JLabel captionLabel;
    private final JButton nextButton;

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

    private void updateScreen() {
        ImageIcon icon = new ImageIcon("tutpics/tut" + (step + 1) + ".png");
        Image image = icon.getImage().getScaledInstance(700, 400, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(image));
        captionLabel.setText(tutorialCaptions[step]);
    }

    private class NextButtonListener implements ActionListener {
        @Override
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
