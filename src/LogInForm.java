import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LogInForm extends JFrame {
    private JPanel logInPanel;
    private JTextField txtUserName;
    private JTextField txtPassword;
    private JButton btnLogIn;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private static LogInForm instance;

    public LogInForm() {
        instance = this;

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(logInPanel, "LogIn");

        setContentPane(mainPanel);

        btnLogIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (Connection connection = database.DatabaseConnection.getConnection()) {
                    String userName = txtUserName.getText();
                    String password = txtPassword.getText();

                    String query = "SELECT * FROM users WHERE user_name = ? AND user_password = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                        preparedStatement.setString(1, userName);
                        preparedStatement.setString(2, password);

                        try (ResultSet resultSet = preparedStatement.executeQuery()) {
                            if (resultSet.next()) {
                                String userType = resultSet.getString("user_type");
                                int id = resultSet.getInt("user_id");
                                int age = resultSet.getInt("user_age");
                                String phone = resultSet.getString("user_phone_no");
                                String status = resultSet.getString("user_status");
                                String profile = resultSet.getString("user_profile");

                                // Add login history
                                addLogHistory(id, "login");

                                switch (userType) {
                                    case "admin" -> switchToInterface(new AdminInterfaceForm(id, userName, userType, password, age, phone, status, profile).getAdminPanel(), profile, "Admin");
                                    case "manager" -> switchToInterface(new ManagerInterFaceForm(id, userName, userType, password, age, phone, status, profile).getManagerPanel(), profile, "Manager");
                                    case "employee" -> switchToInterface(new EmployeeInterfaceDesigner(id, userName, userType, password, age, phone, status, profile).getEmployeePanel(), profile, "Employee");
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid username or password");
                            }
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
                    ex.printStackTrace();
                }
            }
        });

    }

    private void addLogHistory(int userId, String logType) {
        String query = "INSERT INTO log_histories (log_type, log_time, user_id) VALUES (?, NOW(), ?)";
        try (Connection connection = database.DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, logType);
            preparedStatement.setInt(2, userId);
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void switchToInterface(JPanel userInterfacePanel, String path, String name) {
        ProfileImage profileImagePanel = new ProfileImage();
        profileImagePanel.setProfileImage(path);

        JPanel paddedImagePanel = new JPanel(new BorderLayout());
        paddedImagePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        paddedImagePanel.add(profileImagePanel, BorderLayout.CENTER);

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.add(paddedImagePanel, BorderLayout.NORTH);
        containerPanel.add(userInterfacePanel, BorderLayout.CENTER);

        switchToInterfaceSample(containerPanel, name);
    }



    public static void switchToInterfaceSample(JPanel panel, String name) {
        instance.mainPanel.add(panel, name);
        instance.cardLayout.show(instance.mainPanel, name);
    }

    public static void switchToLogin(int userId) {
        instance.addLogHistory(userId, "logout");
        instance.cardLayout.show(instance.mainPanel, "LogIn");
    }


    public static void main(String[] args) {
        LogInForm frame = new LogInForm();
        frame.setTitle("LogIn Form");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setBounds(300, 200, 1000, 600);
        frame.setVisible(true);
    }
}
