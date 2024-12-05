import ui.ViewStudents;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminInterfaceForm extends JPanel {
    private JPanel adminPanel;
    private JButton btnAccountManagement;
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

//    private ImageIcon imageIcon;


    public AdminInterfaceForm(int id, String userName, String userType, String password, int age, String phone, String status, String profile) {
        txtId.setText(Integer.toString(id));
        txtUserName.setText(userName);
        txtTitle.setText(userName);
        txtPassword.setText(password);
        txtAge.setText(age+"");
        txtPhNo.setText(phone);
        txtStatus.setText(status);
        txtAccType.setText(userType);

//        imageIcon = new ImageIcon("images/default_profile.png");
//
//        JLabel imageLabel = new JLabel(imageIcon);
//        this.adminPanel.add(imageLabel);

        this.add(adminPanel);
        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LogInForm.switchToLogin(id);
            }
        });

        btnAccountManagement.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (status.equals("locked")) {
                    JOptionPane.showMessageDialog(null, "Your account has been locked. You can't access to the functions.");
                } else {
                    LogInForm.switchToInterfaceSample(new AccountManagementInterfaceForm(id, userName, userType, password, age, phone, status, profile).getAccountManagementPanel(), "Account Management");
                }
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

    public JPanel getAdminPanel() {
        return adminPanel;
    }
}
