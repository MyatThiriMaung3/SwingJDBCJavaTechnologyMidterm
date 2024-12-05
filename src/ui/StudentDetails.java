package ui;

import studentDetailsFunction.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentDetails {

    public static void showStudentDetails(int studentId, String studentName) {
        JFrame frame = new JFrame("Student Details - " + studentName);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Panel thông tin sinh viên
        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        JLabel idLabel = new JLabel("Student ID: " + studentId);
        JLabel nameLabel = new JLabel("Name: " + studentName);
        JLabel ageLabel = new JLabel("Age:");
        JLabel majorLabel = new JLabel("Major:");
        JLabel addressLabel = new JLabel("Address:");

        JLabel ageValue = new JLabel();
        JLabel majorValue = new JLabel();
        JLabel addressValue = new JLabel();

        // Tải thông tin sinh viên
        StudentInfoHandler.loadStudentInfo(studentId, ageValue, majorValue, addressValue);

        infoPanel.add(idLabel);
        infoPanel.add(new JLabel()); // Empty
        infoPanel.add(nameLabel);
        infoPanel.add(new JLabel()); // Empty
        infoPanel.add(ageLabel);
        infoPanel.add(ageValue);
        infoPanel.add(majorLabel);
        infoPanel.add(majorValue);
        infoPanel.add(addressLabel);
        infoPanel.add(addressValue);

        frame.add(infoPanel, BorderLayout.NORTH);

        // Panel quản lý chứng chỉ
        JPanel certPanel = new JPanel(new BorderLayout());
        JLabel certTitle = new JLabel("Certificates", JLabel.CENTER);
        DefaultTableModel certModel = new DefaultTableModel(new String[]{"Certificate ID", "Name", "Organization"}, 0);
        JTable certTable = new JTable(certModel);
        JScrollPane certScroll = new JScrollPane(certTable);
        certPanel.add(certTitle, BorderLayout.NORTH);
        certPanel.add(certScroll, BorderLayout.CENTER);

        // Nút chức năng
        JPanel certButtonPanel = new JPanel(new FlowLayout());
        JButton addCertButton = new JButton("Add Certificate");
        JButton deleteCertButton = new JButton("Delete Certificate");
        JButton updateCertButton = new JButton("Update Certificate");
        certButtonPanel.add(addCertButton);
        certButtonPanel.add(deleteCertButton);
        certButtonPanel.add(updateCertButton);

        certPanel.add(certButtonPanel, BorderLayout.SOUTH);
        frame.add(certPanel, BorderLayout.CENTER);

        // Tải danh sách chứng chỉ
        CertificateHandler.loadCertificates(certModel, studentId);

        addCertButton.addActionListener(e ->
                AddCertificateHandler.handleAddCertificate(frame, certModel, studentId));

        deleteCertButton.addActionListener(e ->
                DeleteCertificateHandler.handleDeleteCertificate(frame, certTable, certModel, studentId));

        updateCertButton.addActionListener(e ->
                UpdateCertificateHandler.handleUpdateCertificate(frame, certTable, certModel, studentId));

        // Hiển thị giao diện
        frame.setVisible(true);
    }
}