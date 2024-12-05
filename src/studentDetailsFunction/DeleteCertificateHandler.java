package studentDetailsFunction;

import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeleteCertificateHandler {
    public static void handleDeleteCertificate(JFrame frame, JTable certTable, DefaultTableModel certModel, int studentId) {
        int selectedRow = certTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a certificate to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            int certId = (int) certTable.getValueAt(selectedRow, 0); // Lấy Certificate ID từ dòng được chọn
            int option = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this certificate?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                try {
                    deleteCertificate(certId);
                    JOptionPane.showMessageDialog(frame, "Certificate deleted successfully!");
                    CertificateHandler.loadCertificates(certModel, studentId); // Tải lại danh sách chứng chỉ
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Failed to delete certificate: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private static void deleteCertificate(int certId) throws Exception {
        String sql = "DELETE FROM certificates WHERE certi_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, certId);
            statement.executeUpdate();
        }
    }
}