package studentsFunction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteStudentPanel extends JPanel {
    public DeleteStudentPanel(JTable table, DefaultTableModel model) {
        setLayout(new FlowLayout());

        JButton deleteButton = new JButton("Delete Selected Student");
        add(deleteButton);

        deleteButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Please select a student to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                int studentId = (int) table.getValueAt(selectedRow, 0);
                try {
                    deleteStudentFromDatabase(studentId);
                    JOptionPane.showMessageDialog(null, "Student deleted successfully!");
                    model.removeRow(selectedRow);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Failed to delete student: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void deleteStudentFromDatabase(int studentId) throws Exception {
        String sql = "DELETE FROM students WHERE student_id = ?";
        try (Connection conn = database.DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            statement.executeUpdate();
        }
    }
}