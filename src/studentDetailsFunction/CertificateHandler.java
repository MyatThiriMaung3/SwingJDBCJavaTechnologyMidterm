package studentDetailsFunction;

import database.DatabaseConnection;

import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CertificateHandler {
    public static void loadCertificates(DefaultTableModel model, int studentId) {
        String sql = "SELECT * FROM certificates WHERE student_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, studentId);
            ResultSet rs = statement.executeQuery();
            model.setRowCount(0);
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("certi_id"),
                        rs.getString("certi_name"),
                        rs.getString("certi_organization")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addCertificate(String certName, String org, int studentId) throws Exception {
        String sql = "INSERT INTO certificates (certi_name, certi_organization, student_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setString(1, certName);
            statement.setString(2, org);
            statement.setInt(3, studentId);
            statement.executeUpdate();
        }
    }

    public static void deleteCertificate(int certId) throws Exception {
        String sql = "DELETE FROM certificates WHERE certi_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            statement.setInt(1, certId);
            statement.executeUpdate();
        }
    }

    public static void updateCertificate(int certId, String certName, String org) throws Exception {
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