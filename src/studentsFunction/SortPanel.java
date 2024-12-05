package studentsFunction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SortPanel extends JPanel {
    public SortPanel(JTable table, DefaultTableModel model, DataLoader dataLoader) {
        setLayout(new FlowLayout());

        JLabel sortLabel = new JLabel("Sort By:");
        String[] criteria = {"ID", "Name", "Age", "Major"};
        JComboBox<String> sortComboBox = new JComboBox<>(criteria);
        JButton sortButton = new JButton("Sort");

        add(sortLabel);
        add(sortComboBox);
        add(sortButton);

        sortButton.addActionListener(e -> {
            String selectedCriteria = (String) sortComboBox.getSelectedItem();
            // Sử dụng interface DataLoader để tải lại dữ liệu
            dataLoader.loadStudents(model, selectedCriteria, null, null);
        });
    }
}