import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DatabaseConnection;

public class StudentListInterfaceForm extends JPanel {
    private JPanel studentListPanel;
    private JTable tableStudent;
    private JButton btnBack;

    // Constructor
    public StudentListInterfaceForm(int id, String userName, String userType, String password, int age, String phone, String status, String profile) {
        studentListPanel = new JPanel();
        tableStudent = new JTable();
        btnBack = new JButton("Back");

        // Set the layout of the panel to BorderLayout
        studentListPanel.setLayout(new BorderLayout());

        // Create a JScrollPane to make the table scrollable
        JScrollPane scrollPane = new JScrollPane(tableStudent);

        // Add the JScrollPane to the center of the panel
        studentListPanel.add(scrollPane, BorderLayout.CENTER);

        // Add the Back button to the bottom of the panel
        studentListPanel.add(btnBack, BorderLayout.SOUTH);

        this.add(studentListPanel);
        setupStudentTable();

        btnBack.addActionListener(e -> {
            LogInForm.switchToInterface(new EmployeeInterfaceDesigner(id, userName, userType, password, age, phone, status, profile).getEmployeePanel(), profile, "Employee");
        });
    }

    // Method to set up the JTable and populate data from the database
    private void setupStudentTable() {
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Student ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Age");
        tableModel.addColumn("Major");
        tableModel.addColumn("Address");

        // Fetch data from the database and populate the table
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM students";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Retrieve each column data
                int studentId = rs.getInt("student_id");
                String studentName = rs.getString("student_name");
                int studentAge = rs.getInt("student_age");
                String studentMajor = rs.getString("student_major");
                String studentAddress = rs.getString("student_address");

                // Add a row to the table
                tableModel.addRow(new Object[]{studentId, studentName, studentAge, studentMajor, studentAddress});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Set the model of the table
        tableStudent.setModel(tableModel);
    }

    // Getter method for the student list panel
    public JPanel getStudentListPanel() {
        return studentListPanel;
    }
}
