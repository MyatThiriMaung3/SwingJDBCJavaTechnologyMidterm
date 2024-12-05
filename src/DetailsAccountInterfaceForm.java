import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DetailsAccountInterfaceForm extends JPanel {
    private JPanel detailsPanel;
    private JButton btnBack;
    private JTable logInOutTable;
    private JLabel txtTitle;
    private JLabel txtAccType;
    private JLabel txtStatus;
    private JLabel txtPhNo;
    private JLabel txtAge;
    private JLabel txtPassword;
    private JLabel txtUserName;
    private JLabel txtId;

    public DetailsAccountInterfaceForm(int userId, int id, String userName, String userType, String password, int age, String phone, String status, String profile) {
        detailsPanel = new JPanel();
        btnBack = new JButton("Back");
        logInOutTable = new JTable();
        txtTitle = new JLabel("Account Details");
        txtAccType = new JLabel("Account Type");
        txtStatus = new JLabel("Account Status");
        txtPhNo = new JLabel("Phone Number");
        txtAge = new JLabel("Age");
        txtPassword = new JLabel("Password");
        txtUserName = new JLabel("User Name");
        txtId = new JLabel("Account ID");

        // Setting the layout for better positioning of components
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));

        // Adding labels and the table
        detailsPanel.add(txtTitle);
        detailsPanel.add(Box.createVerticalStrut(10)); // Adding some space
        detailsPanel.add(txtId);
        detailsPanel.add(txtUserName);
        detailsPanel.add(txtPassword);
        detailsPanel.add(txtAccType);
        detailsPanel.add(txtStatus);
        detailsPanel.add(txtPhNo);
        detailsPanel.add(txtAge);
        detailsPanel.add(Box.createVerticalStrut(10)); // Adding some space before the table
        detailsPanel.add(new JScrollPane(logInOutTable)); // Scroll pane for the table
        detailsPanel.add(Box.createVerticalStrut(10)); // Adding some space before the button
        detailsPanel.add(btnBack);

        // Set the layout for the panel
        this.setLayout(new BorderLayout());
        this.add(detailsPanel, BorderLayout.CENTER);

        // Back button action listener
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogInForm.switchToInterfaceSample(new AccountManagementInterfaceForm(id, userName, userType, password, age, phone, status, profile).getAccountManagementPanel(), "AccountManagementInterface");
            }
        });

        // Load user details and log history
        loadUserDetails(userId);
        loadLogHistory(userId);
    }

    // Method to load user details into the labels
    private void loadUserDetails(int userId) {
        // SQL query to fetch user details for the given userId
        String query = "SELECT user_id, user_name, user_type, user_status, user_phone_no, user_age, user_password FROM users WHERE user_id = ?";

        try (Connection conn = database.DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("user_id");
                String userName = rs.getString("user_name");
                String userType = rs.getString("user_type");
                String status = rs.getString("user_status");
                String phone = rs.getString("user_phone_no");
                int age = rs.getInt("user_age");
                String password = rs.getString("user_password");

                // Set the retrieved values to the respective labels
                txtId.setText("Account ID: " + id);
                txtUserName.setText("User Name: " + userName);
                txtPassword.setText("Password: " + password);
                txtTitle.setText(userName);
                txtStatus.setText("Account Status: " + status);
                txtAccType.setText("Account Type: " + userType);
                txtPhNo.setText("Phone Number: " + phone);
                txtAge.setText("Age: " + age);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to load log history into the JTable
    private void loadLogHistory(int userId) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Log ID");
        model.addColumn("Log Type");
        model.addColumn("Log Time");

        // SQL query to fetch the log history for the given userId
        String query = "SELECT log_id, log_type, log_time FROM log_histories WHERE user_id = ?";

        try (Connection conn = database.DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int logId = rs.getInt("log_id");
                String logType = rs.getString("log_type");
                Timestamp logTime = rs.getTimestamp("log_time");
                model.addRow(new Object[]{logId, logType, logTime});
            }

            // Set the model for the JTable to display the data
            logInOutTable.setModel(model);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JPanel getDetailsPanel() {
        return detailsPanel;
    }
}
