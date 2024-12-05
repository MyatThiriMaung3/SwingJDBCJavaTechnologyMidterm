package ui;

import javax.swing.*;
import java.awt.*;

public class MainDashboard {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JLabel title = new JLabel("Welcome to Student Management System", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(title, BorderLayout.NORTH);

        JButton viewStudentsButton = new JButton("View Students");


        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(viewStudentsButton);

        frame.add(panel, BorderLayout.CENTER);

        viewStudentsButton.addActionListener(e -> {
            ViewStudents.main(null);
        });

        frame.setVisible(true);
    }
}