package main.java.gui;
import main.java.database.Database;
import main.java.models.User;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.GroupLayout;


public class RegisterMenu extends JFrame {
    private final JFrame PreviousFrame;

    public RegisterMenu(JFrame PreviousFrame) {
        this.PreviousFrame = PreviousFrame;
        initComponents();
    }

    private void RegisterButtonActionPerformed(ActionEvent e)  {
        if (Database.AlreadyExisted(InputUserName.getText())) {
          RegisterButton.setBackground(Color.red);
          RegisterButton.setForeground(Color.BLACK);
            RegisterButton.setText("Username Already exists !");
            tryAgainButton.setVisible(true);

    } else {
            User user = new User(InputUserName.getText(), InputPassword.getPassword());
            Database.InsertInToUsers(user);
            RegisterButton.setBackground(Color.GREEN);
            RegisterButton.setText("Registered successfully !");
        }
    }

    private void PreviousButtonActionPerformed(ActionEvent e) {
        RegisterFrame.dispose();
        PreviousFrame.setVisible(true);
    }

    private void RegisterFrameWindowClosed(WindowEvent e) {
        RegisterFrame.dispose();
        PreviousFrame.setVisible(true);
    }

    private void tryAgainBttonActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void tryAgainButtonActionPerformed(ActionEvent e) {
        new RegisterMenu(PreviousFrame);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        RegisterFrame = new JFrame();
        MainBackground = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        UsernameLabel = new JLabel();
        scrollPane1 = new JScrollPane();
        InputUserName = new JTextArea();
        PasswordLabel = new JLabel();
        InputPassword = new JPasswordField();
        RegisterButton = new JButton();
        PreviousButton = new JButton();
        tryAgainButton = new JButton();

        //======== RegisterFrame ========
        {
            RegisterFrame.setResizable(false);
            RegisterFrame.setMinimumSize(new Dimension(380, 605));
            RegisterFrame.setMaximizedBounds(new Rectangle(530, 60, 380, 605));
            RegisterFrame.setBackground(new Color(0, 112, 192));
            RegisterFrame.setTitle("Register Menu");
            RegisterFrame.setFont(new Font("Calibri", Font.PLAIN, 14));
            RegisterFrame.setIconImage(new ImageIcon(getClass().getResource("/main/resources/icons/Logo.jpg")).getImage());
            RegisterFrame.setVisible(true);
            RegisterFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            RegisterFrame.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
            RegisterFrame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    RegisterFrameWindowClosed(e);
                }
            });
            var RegisterFrameContentPane = RegisterFrame.getContentPane();

            //======== MainBackground ========
            {
                MainBackground.setMaximumSize(new Dimension(380, 605));
                MainBackground.setMinimumSize(new Dimension(380, 605));
                MainBackground.setBackground(new Color(0, 112, 192));
                MainBackground.setFocusable(false);

                //---- label1 ----
                label1.setText("Quiz Of Kings");
                label1.setFont(new Font("Calibri", Font.PLAIN, 54));
                label1.setForeground(Color.white);

                //---- label2 ----
                label2.setText("text");
                label2.setIcon(new ImageIcon(getClass().getResource("/main/resources/icons/Logo.jpg")));

                //---- UsernameLabel ----
                UsernameLabel.setText("Username");
                UsernameLabel.setForeground(Color.white);

                //======== scrollPane1 ========
                {

                    //---- InputUserName ----
                    InputUserName.setBackground(Color.white);
                    InputUserName.setForeground(Color.darkGray);
                    InputUserName.setLineWrap(true);
                    InputUserName.setTabSize(10);
                    InputUserName.setAlignmentX(1.5F);
                    InputUserName.setAlignmentY(1.5F);
                    scrollPane1.setViewportView(InputUserName);
                }

                //---- PasswordLabel ----
                PasswordLabel.setText("Password");
                PasswordLabel.setForeground(Color.white);

                //---- InputPassword ----
                InputPassword.setBackground(Color.white);
                InputPassword.setForeground(Color.darkGray);

                //---- RegisterButton ----
                RegisterButton.setText("Register");
                RegisterButton.setForeground(Color.gray);
                RegisterButton.setBackground(new Color(0, 32, 96));
                RegisterButton.setFocusable(false);
                RegisterButton.addActionListener(e -> RegisterButtonActionPerformed(e));

                //---- PreviousButton ----
                PreviousButton.setText("previous");
                PreviousButton.setBackground(new Color(137, 0, 0, 209));
                PreviousButton.setFont(new Font("Lucida Sans Unicode", Font.BOLD | Font.ITALIC, 12));
                PreviousButton.setForeground(Color.lightGray);
                PreviousButton.setAlignmentX(16.0F);
                PreviousButton.setFocusable(false);
                PreviousButton.addActionListener(e -> PreviousButtonActionPerformed(e));

                //---- tryAgainButton ----
                tryAgainButton.setText("Try again");
                tryAgainButton.setFocusable(false);
                tryAgainButton.setVisible(false);
                tryAgainButton.addActionListener(e -> tryAgainButtonActionPerformed(e));

                GroupLayout MainBackgroundLayout = new GroupLayout(MainBackground);
                MainBackground.setLayout(MainBackgroundLayout);
                MainBackgroundLayout.setHorizontalGroup(
                    MainBackgroundLayout.createParallelGroup()
                        .addGroup(MainBackgroundLayout.createSequentialGroup()
                            .addContainerGap(49, Short.MAX_VALUE)
                            .addGroup(MainBackgroundLayout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, MainBackgroundLayout.createSequentialGroup()
                                    .addComponent(label1)
                                    .addGap(44, 44, 44))
                                .addGroup(GroupLayout.Alignment.TRAILING, MainBackgroundLayout.createSequentialGroup()
                                    .addComponent(label2, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)
                                    .addGap(125, 125, 125))
                                .addGroup(GroupLayout.Alignment.TRAILING, MainBackgroundLayout.createSequentialGroup()
                                    .addGroup(MainBackgroundLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(scrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(UsernameLabel, GroupLayout.Alignment.LEADING)
                                        .addComponent(PasswordLabel, GroupLayout.Alignment.LEADING)
                                        .addComponent(InputPassword, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(RegisterButton, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                                    .addGap(85, 85, 85))
                                .addGroup(GroupLayout.Alignment.TRAILING, MainBackgroundLayout.createSequentialGroup()
                                    .addGroup(MainBackgroundLayout.createParallelGroup()
                                        .addComponent(PreviousButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(tryAgainButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                                    .addGap(136, 136, 136))))
                );
                MainBackgroundLayout.setVerticalGroup(
                    MainBackgroundLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, MainBackgroundLayout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(label2, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
                            .addGap(29, 29, 29)
                            .addComponent(label1)
                            .addGap(18, 18, 18)
                            .addComponent(UsernameLabel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(PasswordLabel, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(InputPassword, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addGap(24, 24, 24)
                            .addComponent(RegisterButton, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(tryAgainButton)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(PreviousButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                            .addContainerGap())
                );
            }

            GroupLayout RegisterFrameContentPaneLayout = new GroupLayout(RegisterFrameContentPane);
            RegisterFrameContentPane.setLayout(RegisterFrameContentPaneLayout);
            RegisterFrameContentPaneLayout.setHorizontalGroup(
                RegisterFrameContentPaneLayout.createParallelGroup()
                    .addComponent(MainBackground, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            RegisterFrameContentPaneLayout.setVerticalGroup(
                RegisterFrameContentPaneLayout.createParallelGroup()
                    .addComponent(MainBackground, GroupLayout.DEFAULT_SIZE, 565, Short.MAX_VALUE)
            );
            RegisterFrame.pack();
            RegisterFrame.setLocationRelativeTo(RegisterFrame.getOwner());
        }
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JFrame RegisterFrame;
    private JPanel MainBackground;
    private JLabel label1;
    private JLabel label2;
    private JLabel UsernameLabel;
    private JScrollPane scrollPane1;
    private JTextArea InputUserName;
    private JLabel PasswordLabel;
    private JPasswordField InputPassword;
    private JButton RegisterButton;
    private JButton PreviousButton;
    private JButton tryAgainButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
