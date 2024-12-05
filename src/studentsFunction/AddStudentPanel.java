package studentsFunction;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddStudentPanel extends JPanel {
    public AddStudentPanel() {
        setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Student");
        add(addButton);

        addButton.addActionListener(e -> {
            JTextField nameField = new JTextField();
            JTextField ageField = new JTextField();
            JTextField majorField = new JTextField();
            JTextField addressField = new JTextField();

            Object[] fields = {
                    "Name:", nameField,
                    "Age:", ageField,
                    "Major:", majorField,
                    "Address:", addressField
            };

            int option = JOptionPane.showConfirmDialog(null, fields, "Add New Student", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String major = majorField.getText();
                    String address = addressField.getText();
                    addStudentToDatabase(name, age, major, address);
                    JOptionPane.showMessageDialog(null, "Student added successfully!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Failed to add student: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void addStudentToDatabase(String name, int age, String major, String address) throws Exception {
        String sql = "INSERT INTO students (student_name, student_age, student_major, student_address) VALUES (?, ?, ?, ?)";
        try (Connection conn = database.DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setInt(2, age);
            statement.setString(3, major);
            statement.setString(4, address);
            statement.executeUpdate();
        }
    }
}