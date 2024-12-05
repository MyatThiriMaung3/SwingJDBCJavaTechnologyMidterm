import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class AccountManagementInterfaceForm extends JPanel {
    private JTable tblUser;
    private JButton btnCreateNewUser;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnDetails;
    private JButton btnBack;
    private JPanel accountManagementPanel;

    public AccountManagementInterfaceForm(int id, String userName, String userType, String password, int age, String phone, String status, String profile) {
        tblUser = new JTable();
        btnCreateNewUser = new JButton("Create New User");
        btnEdit = new JButton("Edit");
        btnDelete = new JButton("Delete");
        btnDetails = new JButton("Details");
        btnBack = new JButton("Back");
        accountManagementPanel = new JPanel();
        accountManagementPanel.setLayout(new BorderLayout());

        // Add a scrollable table at the top
        JScrollPane scrollPane = new JScrollPane(tblUser);
        accountManagementPanel.add(scrollPane, BorderLayout.CENTER);

        // Create a panel for the buttons and use FlowLayout to align them side by side
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(btnCreateNewUser);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnDetails);
        buttonPanel.add(btnBack);

        // Add the button panel to the bottom
        accountManagementPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(accountManagementPanel);

        // Set up the table model for displaying users
        setupUserTable();

        // Action listener for "Create New User" button
        btnCreateNewUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch to Create Account form (you can implement the actual form)
                LogInForm.switchToInterfaceSample(new CreateAccountInterfaceForm(id, userName, userType, password, age, phone, status, profile).getCreateAccountPanel(), "CreateAccount");
            }
        });

        // Action listener for "Back" button
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch back to Admin Interface
                LogInForm.switchToInterface(new AdminInterfaceForm(id, userName, userType, password, age, phone, status, profile).getAdminPanel(), profile, "Admin");
            }
        });

        // Action listener for "Edit" button
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblUser.getSelectedRow();
                if (selectedRow != -1) {
                    int userId = (int) tblUser.getValueAt(selectedRow, 0);
                    String userName = (String) tblUser.getValueAt(selectedRow, 1);
                    int userAge = (int) tblUser.getValueAt(selectedRow, 2);
                    String userPhoneNo = (String) tblUser.getValueAt(selectedRow, 3);
                    String userStatus = (String) tblUser.getValueAt(selectedRow, 4);
                    String userType = (String) tblUser.getValueAt(selectedRow, 5);
                    String userProfile = (String) tblUser.getValueAt(selectedRow, 6);

                    // Open a dialog for editing the user details
                    openEditDialog(userId, userName, userAge, userPhoneNo, userStatus, userType, userProfile);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a user to edit.");
                }
            }
        });

        // Action listener for "Delete" button
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row index
                int selectedRow = tblUser.getSelectedRow();
                if (selectedRow != -1) {
                    int userId = (int) tblUser.getValueAt(selectedRow, 0);

                    // Show a confirmation dialog
                    int confirm = JOptionPane.showConfirmDialog(null,
                            "Are you sure you want to delete the selected user?", "Confirm Deletion",
                            JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                    if (confirm == JOptionPane.YES_OPTION) {
                        // Delete the user from the database
                        deleteUser(userId);
                        // Refresh the table after deletion
                        setupUserTable();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a user to delete.");
                }
            }
        });

        // Action listener for "Details" button
        btnDetails.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row index
                int selectedRow = tblUser.getSelectedRow();
                if (selectedRow != -1) {
                    int userId = (int) tblUser.getValueAt(selectedRow, 0);
                    String userProfile = (String) tblUser.getValueAt(selectedRow, 6);

                    LogInForm.switchToInterface(new DetailsAccountInterfaceForm(userId, id, userName, userType, password, age, phone, status, profile).getDetailsPanel(), userProfile, "DetailsAccount");
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a user to view details.");
                }
            }
        });
    }

    // Method to open the dialog for editing user details
    private void openEditDialog(int userId, String userName, int userAge, String userPhoneNo, String userStatus, String userType, String userProfile) {
        JTextField txtAge = new JTextField(String.valueOf(userAge));
        JTextField txtPhone = new JTextField(userPhoneNo);
        JTextField txtStatus = new JTextField(userStatus);
        JTextField txtUserType = new JTextField(userType);
        JTextField txtProfile = new JTextField(userProfile);

        // Create a panel for the edit dialog
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(new JLabel("Age:"));
        panel.add(txtAge);
        panel.add(new JLabel("Phone:"));
        panel.add(txtPhone);
        panel.add(new JLabel("Status:"));
        panel.add(txtStatus);
        panel.add(new JLabel("User Type:"));
        panel.add(txtUserType);
        panel.add(new JLabel("Profile:"));
        panel.add(txtProfile);

        int option = JOptionPane.showConfirmDialog(this, panel, "Edit User Details", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            // Get the updated values
            int updatedAge = Integer.parseInt(txtAge.getText());
            String updatedPhone = txtPhone.getText();
            String updatedStatus = txtStatus.getText();
            String updatedUserType = txtUserType.getText();
            String updatedProfile = txtProfile.getText();

            updateUser(userId, updatedAge, updatedPhone, updatedStatus, updatedUserType, updatedProfile);
            setupUserTable();
        }
    }

    // Method to update the user in the database
    private void updateUser(int userId, int age, String phone, String status, String userType, String profile) {
        try (Connection conn = database.DatabaseConnection.getConnection()) {
            String query = "UPDATE users SET user_age = ?, user_phone_no = ?, user_status = ?, user_type = ?, user_profile = ? WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, age);
            stmt.setString(2, phone);
            stmt.setString(3, status);
            stmt.setString(4, userType);
            stmt.setString(5, profile);
            stmt.setInt(6, userId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "User updated successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error updating user.");
        }
    }

    public JPanel getAccountManagementPanel() {
        return accountManagementPanel;
    }

    // Method to set up the user table and populate it with data from the database
    private void setupUserTable() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("User ID");
        tableModel.addColumn("User Name");
        tableModel.addColumn("Age");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Status");
        tableModel.addColumn("User Type");
        tableModel.addColumn("Profile");

        // Query the database for all users
        try (Connection conn = database.DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Add each row of data to the table model
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getInt("user_age"),
                        rs.getString("user_phone_no"),
                        rs.getString("user_status"),
                        rs.getString("user_type"),
                        rs.getString("user_profile")
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set the table model
        tblUser.setModel(tableModel);


    }

    // Method to delete the user from the database
    private void deleteUser(int userId) {
        try (Connection conn = database.DatabaseConnection.getConnection()) {
            String query = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "User deleted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
