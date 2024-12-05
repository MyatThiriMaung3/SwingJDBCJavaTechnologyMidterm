import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateAccountInterfaceForm extends JFrame {
    private JPanel createAccountPanel;
    private JButton btnCreate, btnBack;
    private JTextField txtBoxUserName, txtBoxPassword, txtBoxAge, txtBoxStatus, txtBoxAccType, txtBoxPhNo;

    public CreateAccountInterfaceForm(int id, String userName, String userType, String password, int age, String phone, String status, String profile) {
        // Initialize components
        createAccountPanel = new JPanel();
        btnCreate = new JButton("Create");
        btnBack = new JButton("Back");
        txtBoxUserName = new JTextField(20);
        txtBoxPassword = new JTextField(20);
        txtBoxAge = new JTextField(20);
        txtBoxStatus = new JTextField(20);
        txtBoxAccType = new JTextField(20);
        txtBoxPhNo = new JTextField(20);

        // Layout manager settings
        createAccountPanel.setLayout(new BoxLayout(createAccountPanel, BoxLayout.Y_AXIS));

        // Panel for labels and text fields
        JPanel inputPanel = new JPanel(new GridLayout(6, 2, 5, 5)); // 6 rows, 2 columns, with gaps
        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(txtBoxUserName);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(txtBoxPassword);
        inputPanel.add(new JLabel("Age:"));
        inputPanel.add(txtBoxAge);
        inputPanel.add(new JLabel("Status:"));
        inputPanel.add(txtBoxStatus);
        inputPanel.add(new JLabel("Account Type:"));
        inputPanel.add(txtBoxAccType);
        inputPanel.add(new JLabel("Phone Number:"));
        inputPanel.add(txtBoxPhNo);

        // Panel for buttons (Create, Back, Details, Delete, Edit)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnCreate);
        buttonPanel.add(btnBack);

        // Add components to the main panel
        createAccountPanel.add(inputPanel);
        createAccountPanel.add(buttonPanel);

        this.add(createAccountPanel);

        // Action for Back button
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogInForm.switchToInterfaceSample(new AccountManagementInterfaceForm(id, userName, userType, password, age, phone, status, profile).getAccountManagementPanel(), "AccountManagementInterface");
            }
        });

        // Action for Create button
        btnCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the values from text fields
                String userName = txtBoxUserName.getText();
                String password = txtBoxPassword.getText();
                int age = Integer.parseInt(txtBoxAge.getText());
                String status = txtBoxStatus.getText();
                String accType = txtBoxAccType.getText();
                String phone = txtBoxPhNo.getText();

                // Insert the new user into the database
                boolean isCreated = createNewUser(userName, password, age, status, accType, phone);
                if (isCreated) {
                    JOptionPane.showMessageDialog(null, "Account Created Successfully!");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to Create Account.");
                }
            }
        });

    }

    // Method to insert a new user into the database
    private boolean createNewUser(String userName, String password, int age, String status, String accType, String phone) {
        String query = "INSERT INTO users (user_name, user_age, user_phone_no, user_status, user_type, user_password, user_profile) " +
                "VALUES (?, ?, ?, ?, ?, ?, '/images/default_profile.jpg')";
        try (Connection conn = database.DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set the values for the prepared statement
            stmt.setString(1, userName);
            stmt.setInt(2, age);
            stmt.setString(3, phone);
            stmt.setString(4, status);
            stmt.setString(5, accType);
            stmt.setString(6, password);

            // Execute the update
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if a row was inserted

        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Return false if there was an error
        }
    }

    public JPanel getCreateAccountPanel() {
        return createAccountPanel;
    }
}
