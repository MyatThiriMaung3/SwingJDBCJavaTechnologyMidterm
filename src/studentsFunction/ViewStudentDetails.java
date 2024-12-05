package studentsFunction;

import ui.StudentDetails;

import javax.swing.*;

public class ViewStudentDetails {
    public static void handleViewDetails(JTable table) {
        int selectedRow = table.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Please select a student to view details.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Lấy thông tin từ dòng được chọn
        int studentId = (int) table.getValueAt(selectedRow, 0);
        String studentName = (String) table.getValueAt(selectedRow, 1);

        // Mở màn hình chi tiết sinh viên
        StudentDetails.showStudentDetails(studentId, studentName);
    }
}