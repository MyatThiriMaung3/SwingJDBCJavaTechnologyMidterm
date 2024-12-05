package ui;

import studentsFunction.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ViewStudents implements DataLoader {
    public static void main(String[] args) {
        JFrame frame = new JFrame("View Students");
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setLayout(new BorderLayout());

        // Bảng hiển thị sinh viên
        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Major", "Address"}, 0);
        table.setModel(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel chức năng
        ViewStudents dataLoader = new ViewStudents(); // Implement DataLoader
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        buttonPanel.add(new AddStudentPanel());
        buttonPanel.add(new DeleteStudentPanel(table, model));
        buttonPanel.add(new SortPanel(table, model, dataLoader)); // Truyền DataLoader
        buttonPanel.add(new SearchPanel(table, model, dataLoader)); // Truyền DataLoader
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Tải dữ liệu ban đầu
        dataLoader.loadStudents(model, null, null, null);

        // Panel chứa nút View Details
        JPanel viewDetailsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Căn giữa nút View Details
        JButton viewDetailsButton = new JButton("View Details");
        viewDetailsButton.addActionListener(e -> ViewStudentDetails.handleViewDetails(table));
        viewDetailsPanel.add(viewDetailsButton);
        buttonPanel.add(viewDetailsPanel);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    @Override
    public void loadStudents(DefaultTableModel model, String sortCriteria, String searchCriteria, String searchText) {
        StringBuilder sql = new StringBuilder("SELECT * FROM students");

        if (searchCriteria != null && searchText != null && !searchText.isEmpty()) {
            sql.append(" WHERE ");
            switch (searchCriteria) {
                case "Name":
                    sql.append("student_name LIKE ?");
                    break;
                case "Major":
                    sql.append("student_major LIKE ?");
                    break;
                case "Age":
                    sql.append("student_age = ?");
                    break;
            }
        }

        if (sortCriteria != null) {
            sql.append(" ORDER BY ");
            switch (sortCriteria) {
                case "Name":
                    sql.append("student_name");
                    break;
                case "Age":
                    sql.append("student_age");
                    break;
                case "Major":
                    sql.append("student_major");
                    break;
                default:
                    sql.append("student_id");
            }
        }

        try (Connection conn = database.DatabaseConnection.getConnection()) {
            assert conn != null;
            try (PreparedStatement statement = conn.prepareStatement(sql.toString())) {

                if (searchCriteria != null && searchText != null && !searchText.isEmpty()) {
                    if (searchCriteria.equals("Age")) {
                        statement.setInt(1, Integer.parseInt(searchText));
                    } else {
                        statement.setString(1, "%" + searchText + "%");
                    }
                }

                ResultSet resultSet = statement.executeQuery();
                model.setRowCount(0);

                while (resultSet.next()) {
                    int id = resultSet.getInt("student_id");
                    String name = resultSet.getString("student_name");
                    int age = resultSet.getInt("student_age");
                    String major = resultSet.getString("student_major");
                    String address = resultSet.getString("student_address");

                    model.addRow(new Object[]{id, name, age, major, address});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Failed to load students data.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}