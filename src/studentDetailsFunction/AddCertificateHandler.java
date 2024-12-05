package studentDetailsFunction;

import database.DatabaseConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddCertificateHandler {
    public static void handleAddCertificate(JFrame frame, DefaultTableModel certModel, int studentId) {
        JTextField certNameField = new JTextField();
        JTextField orgField = new JTextField();

        Object[] fields = {
                "Certificate Name:", certNameField,
                "Organization:", orgField
        };

        int option = JOptionPane.showConfirmDialog(frame, fields, "Add Certificate", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String certName = certNameField.getText();
                String org = orgField.getText();
                addCertificate(certName, org, studentId);
                JOptionPane.showMessageDialog(frame, "Certificate added successfully!");
                CertificateHandler.loadCertificates(certModel, studentId); // Tải lại danh sách chứng chỉ
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Failed to add certificate: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void addCertificate(String certName, String org, int studentId) throws Exception {
        String sql = "INSERT INTO certificates (certi_name, certi_organization, student_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, certName);
            statement.setString(2, org);
            statement.setInt(3, studentId);
            statement.executeUpdate();
        }
    }
}