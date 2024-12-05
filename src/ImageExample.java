import com.mysql.cj.log.Log;
import database.DatabaseConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ImageExample extends JPanel {
    private static String selectedImagePath = null; // To store the selected image path
    private JPanel imagePanel;
    private JPanel buttonPanel;
    String updatedProfile = null;

    public ImageExample(int id, String userName, String userType, String password, int age, String phone, String status, String profile) {
        setLayout(new BorderLayout()); // Use BorderLayout for the main panel

        // Create the image panel
        imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.X_AXIS));

        JLabel imageLabel0 = createSelectableLabel("/images/default_profile.jpg");
        JLabel imageLabel1 = createSelectableLabel("/images/profile1.jpg");
        JLabel imageLabel2 = createSelectableLabel("/images/profile2.jpg");
        JLabel imageLabel3 = createSelectableLabel("/images/profile3.jpg");
        JLabel imageLabel4 = createSelectableLabel("/images/profile4.jpg");
        JLabel imageLabel5 = createSelectableLabel("/images/profile5.jpg");
        JLabel imageLabel6 = createSelectableLabel("/images/profile6.jpg");
        JLabel imageLabel7 = createSelectableLabel("/images/profile7.jpg");
        JLabel imageLabel8 = createSelectableLabel("/images/profile8.jpg");

        // Add the labels to the image panel
        addingImagesToPanel(imagePanel, imageLabel0, imageLabel1, imageLabel2, imageLabel3, imageLabel4, imageLabel5, imageLabel6, imageLabel7, imageLabel8);

        // Create the button panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Center the buttons with spacing

        // Create the Back button
        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            if (updatedProfile == null) {
                updatedProfile = profile;
            }

            switch (userType) {
                case "admin" -> LogInForm.switchToInterface(new AdminInterfaceForm(id, userName, userType, password, age, phone, status, updatedProfile).getAdminPanel(), updatedProfile, "Admin");
                case "manager" -> LogInForm.switchToInterface(new ManagerInterFaceForm(id, userName, userType, password, age, phone, status, updatedProfile).getManagerPanel(), updatedProfile, "Manager");
                case "employee" -> LogInForm.switchToInterface(new EmployeeInterfaceDesigner(id, userName, userType, password, age, phone, status, updatedProfile).getEmployeePanel(), updatedProfile, "Employee");
            }
        });

        // Create the Apply button
        JButton btnApply = new JButton("Apply Image");
        // Inside the Apply button's ActionListener
        btnApply.addActionListener(e -> {
            if (selectedImagePath != null) {
                // Call the method to update the profile image in the database
                boolean isUpdated = updateProfileImageInDatabase(id, selectedImagePath);

                if (isUpdated) {
                    JOptionPane.showMessageDialog(null, "Profile image updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    updatedProfile = selectedImagePath;
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update profile image. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Show a message to select an image
                JOptionPane.showMessageDialog(null, "Please select an image first!", "No Image Selected", JOptionPane.WARNING_MESSAGE);
            }
        });


        // Add buttons to the button panel
        buttonPanel.add(btnBack);
        buttonPanel.add(btnApply);

        // Add components to the main panel
        add(imagePanel, BorderLayout.CENTER); // Add the image panel to the center
        add(buttonPanel, BorderLayout.SOUTH); // Add the button panel to the bottom
    }

    public JPanel getImagePanel() {
        return imagePanel;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    private boolean updateProfileImageInDatabase(int userId, String imagePath) {
        String updateQuery = "UPDATE users SET user_profile = ? WHERE user_id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {

            preparedStatement.setString(1, imagePath);
            preparedStatement.setInt(2, userId);

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if the update was successful
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }


    private static void addingImagesToPanel(JPanel panel, JLabel... labels) {
        for (JLabel label : labels) {
            panel.add(label);
            panel.add(Box.createRigidArea(new Dimension(10, 0))); // Add spacing between images
        }
    }

    private static JLabel createSelectableLabel(String imagePath) {
        ImageIcon originalIcon = new ImageIcon(ImageExample.class.getResource(imagePath)); // Adjust path as needed
        Image originalImage = originalIcon.getImage(); // Get the original image
        Image scaledImage = originalImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Scale the image to 100x100
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel imageLabel = new JLabel(scaledIcon);
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Add a border for visual feedback

        // Add a MouseListener for interaction
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Set the selected image path
                selectedImagePath = imagePath;

                // Reset all other borders
                Component[] components = imageLabel.getParent().getComponents();
                for (Component component : components) {
                    if (component instanceof JLabel) {
                        ((JLabel) component).setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                    }
                }

                // Highlight the selected image
                imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                imageLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Highlight on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!imagePath.equals(selectedImagePath)) {
                    imageLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY)); // Revert on hover out
                }
            }
        });

        return imageLabel;
    }
}
