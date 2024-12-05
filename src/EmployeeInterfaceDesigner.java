import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeInterfaceDesigner extends JPanel {
    private JPanel employeePanel;
    private JButton btnViewStudentList;
    private JButton btnChangeProfilePicture;
    private JButton btnLogOut;
    private JLabel txtTitle;
    private JLabel txtAccType;
    private JLabel txtStatus;
    private JLabel txtPhNo;
    private JLabel txtAge;
    private JLabel txtPassword;
    private JLabel txtUserName;
    private JLabel txtId;

    public EmployeeInterfaceDesigner(int id, String userName, String userType, String password, int age, String phone, String status, String profile) {
        txtId.setText(Integer.toString(id));
        txtUserName.setText(userName);
        txtPassword.setText(password);
        txtTitle.setText(userName);
        txtStatus.setText(status);
        txtAccType.setText(userType);
        txtPhNo.setText(phone);
        txtAge.setText(age+"");


        this.add(employeePanel);

        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogInForm.switchToLogin(id);
            }
        });

        btnViewStudentList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (status.equals("locked")) {
                    JOptionPane.showMessageDialog(null, "Your account has been locked. You can't access to the functions.");
                } else {
                    LogInForm.switchToInterfaceSample(new StudentListInterfaceForm(id, userName, userType, password, age, phone, status, profile).getStudentListPanel(), "Students");
                }
            }
        });

        btnChangeProfilePicture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (status.equals("locked")) {
                    JOptionPane.showMessageDialog(null, "Your account has been locked. You can't access to the functions.");
                } else {
                    LogInForm.switchToInterfaceSample(new ImageExample(id, userName, userType, password, age, phone, status, profile), "Account Management");
                }
            }
        });
    }

    public JPanel getEmployeePanel() {
        return employeePanel;
    }

}
