package studentDetailsFunction;

import database.DatabaseConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class StudentInfoHandler {
    public static void loadStudentInfo(int studentId, JLabel age, JLabel major, JLabel address) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                age.setText(String.valueOf(rs.getInt("student_age")));
                major.setText(rs.getString("student_major"));
                address.setText(rs.getString("student_address"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load student info.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}