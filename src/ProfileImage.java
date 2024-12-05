import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class ProfileImage extends JPanel {
    private BufferedImage profileImage;
    private final String defaultImagePath = "/images/default_profile.jpg"; // Default image path
    private final JLabel imageLabel;

    public ProfileImage() {
        setLayout(new BorderLayout());
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);
        add(imageLabel, BorderLayout.CENTER);
        loadImage(defaultImagePath); // Load the default image initially
    }

    // Method to load and display the image
    private void loadImage(String imagePath) {
        try {
            // Load image as a resource from the classpath
            profileImage = ImageIO.read(getClass().getResource(imagePath));
            ImageIcon icon = new ImageIcon(profileImage.getScaledInstance(150, 150, Image.SCALE_SMOOTH));
            imageLabel.setIcon(icon);
        } catch (IOException | NullPointerException e) {
            JOptionPane.showMessageDialog(this, "Image not found: " + imagePath, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Method to update the profile image
    public void setProfileImage(String imagePath) {
        loadImage(imagePath);
    }
}
