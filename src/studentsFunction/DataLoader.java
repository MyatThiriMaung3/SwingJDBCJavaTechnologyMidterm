package studentsFunction;

import javax.swing.table.DefaultTableModel;

public interface DataLoader {
    void loadStudents(DefaultTableModel model, String sortCriteria, String searchCriteria, String searchText);
}