import ui.ViewStudents;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManagerInterFaceForm extends JPanel {
    private JButton btnStudentManagement;
    private JButton btnChangeProfilePicture;
    private JButton btnLogOut;
    private JLabel txtTitle;
    private JLabel txtId;
    private JLabel txtUserName;
    private JLabel txtPassword;
    private JLabel txtAge;
    private JLabel txtPhNo;
    private JLabel txtStatus;
    private JLabel txtAccType;
    private JPanel managerPanel;

    public ManagerInterFaceForm(int id, String userName, String userType, String password, int age, String phone, String status, String profile) {
        txtId.setText(Integer.toString(id));
        txtUserName.setText(userName);
        txtPassword.setText(password);
        txtTitle.setText(userName);
        txtStatus.setText(status);
        txtAccType.setText(userType);
        txtAge.setText(Integer.toString(age));
        txtPhNo.setText(phone);

        this.add(managerPanel);

        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogInForm.switchToLogin(id);
            }
        });

        btnStudentManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (status.equals("locked")) {
                    JOptionPane.showMessageDialog(null, "Your account has been locked. You can't access to the functions.");
                } else {
                    ViewStudents.main(null);
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

    public JPanel getManagerPanel() {
        return managerPanel;
    }
}
