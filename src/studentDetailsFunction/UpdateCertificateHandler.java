package studentDetailsFunction;

import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateCertificateHandler {
    public static void handleUpdateCertificate(JFrame frame, JTable certTable, DefaultTableModel certModel, int studentId) {
        int selectedRow = certTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a certificate to update.", "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            int certId = (int) certTable.getValueAt(selectedRow, 0); // Lấy Certificate ID từ dòng được chọn
            String currentName = (String) certTable.getValueAt(selectedRow, 1);
            String currentOrg = (String) certTable.getValueAt(selectedRow, 2);

            JTextField certNameField = new JTextField(currentName);
            JTextField orgField = new JTextField(currentOrg);

            Object[] fields = {
                    "Certificate Name:", certNameField,
                    "Organization:", orgField
            };

            int option = JOptionPane.showConfirmDialog(frame, fields, "Update Certificate", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                try {
                    String updatedName = certNameField.getText();
                    String updatedOrg = orgField.getText();
                    updateCertificate(certId, updatedName, updatedOrg);
                    JOptionPane.showMessageDialog(frame, "Certificate updated successfully!");
                    CertificateHandler.loadCertificates(certModel, studentId); // Tải lại danh sách chứng chỉ
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Failed to update certificate: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private static void updateCertificate(int certId, String certName, String org) throws Exception {
        String sql = "UPDATE certificates SET certi_name = ?, certi_organization = ? WHERE certi_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, certName);
            statement.setString(2, org);
            statement.setInt(3, certId);
            statement.executeUpdate();
        }
    }
}