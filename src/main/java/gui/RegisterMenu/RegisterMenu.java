package main.java.gui.RegisterMenu;

import main.java.animations.ColorChangeAnimation;
import main.java.database.Database;
import main.java.errors.GuiError;
import main.java.models.User;
import main.java.utils.GuiValidation;

import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;
import javax.swing.*;
import javax.swing.GroupLayout;

import static main.java.config.GuiConfig.COLOR_DANGER;


public class RegisterMenu extends JFrame {
    private final JFrame PreviousFrame;
    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private static JPanel MainBackground;
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
    private JLabel usernameErrorLabel;
    private JLabel passwordErrorLabel;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    public RegisterMenu(JFrame PreviousFrame) {
        this.PreviousFrame = PreviousFrame;
        initComponents();
    }

    private void RegisterButtonActionPerformed(ActionEvent e) {

        var usernameError = GuiValidation.validateUsername(InputUserName.getText());
        var passwordError = GuiValidation.validatePassword(String.valueOf(InputPassword.getPassword()));

        if (Database.AlreadyExisted(InputUserName.getText())) {

            RegisterButton.setBackground(Color.red);
            RegisterButton.setForeground(Color.BLACK);
            RegisterButton.setText("Username Already exists !");
            tryAgainButton.setVisible(true);

        } else if (usernameError == null && passwordError == null) {

            User user = new User(InputUserName.getText(), InputPassword.getPassword(),1);
            Database.InsertInToUsers(user);
            RegisterButton.setBackground(Color.GREEN);
            RegisterButton.setText("Registered successfully !");

        } else {
            runMainPanelBackgroundColorAnimation(MainBackground);
            updateErrorLabel(usernameError, usernameErrorLabel);
            updateErrorLabel(passwordError, passwordErrorLabel);
        }
    }

    public static void  runMainPanelBackgroundColorAnimation(JPanel MainBackground) {
        Consumer<Color> stepCallback = MainBackground::setBackground;
        Runnable endCallback = () -> MainBackground.setBackground(new Color(0, 112, 192));
        new ColorChangeAnimation(MainBackground.getBackground(), COLOR_DANGER, stepCallback, endCallback).start();
    }

    private void updateErrorLabel(GuiError error, JLabel errorLabel) {
        if (error != null)
            errorLabel.setText(error.getMessage());
        else
            errorLabel.setText("");
    }

    private void PreviousButtonActionPerformed(ActionEvent e) {
        this.dispose();
        PreviousFrame.setVisible(true);
    }

    private void RegisterFrameWindowClosed(WindowEvent e) {
        this.dispose();
        PreviousFrame.setVisible(true);
    }

    private void tryAgainButtonActionPerformed(ActionEvent e) {
        this.setVisible(false);
        new RegisterMenu(PreviousFrame);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
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
        usernameErrorLabel = new JLabel();
        passwordErrorLabel = new JLabel();

        //======== this ========
        setResizable(false);
        setMinimumSize(new Dimension(380, 605));
        setMaximizedBounds(new Rectangle(530, 60, 380, 605));
        setBackground(new Color(0, 112, 192));
        setTitle("Register Menu");
        setFont(new Font("Calibri", Font.PLAIN, 14));
        setIconImage(new ImageIcon(getClass().getResource("/main/resources/icons/Theme/Logo.jpg")).getImage());
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setName("RegisterMenu");
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                RegisterFrameWindowClosed(e);
            }
        });
        var contentPane = getContentPane();

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
            label2.setIcon(new ImageIcon(getClass().getResource("/main/resources/icons/Theme/SmallLogo.jpg")));

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

            //---- usernameErrorLabel ----
            usernameErrorLabel.setForeground(Color.red);
            usernameErrorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

            //---- passwordErrorLabel ----
            passwordErrorLabel.setForeground(Color.red);
            passwordErrorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

            GroupLayout MainBackgroundLayout = new GroupLayout(MainBackground);
            MainBackground.setLayout(MainBackgroundLayout);
            MainBackgroundLayout.setHorizontalGroup(
                MainBackgroundLayout.createParallelGroup()
                    .addGroup(MainBackgroundLayout.createSequentialGroup()
                        .addContainerGap(49, Short.MAX_VALUE)
                        .addGroup(MainBackgroundLayout.createParallelGroup()
                            .addGroup(GroupLayout.Alignment.TRAILING, MainBackgroundLayout.createSequentialGroup()
                                .addGroup(MainBackgroundLayout.createParallelGroup()
                                    .addComponent(PreviousButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tryAgainButton, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
                                .addGap(136, 136, 136))
                            .addGroup(GroupLayout.Alignment.TRAILING, MainBackgroundLayout.createSequentialGroup()
                                .addComponent(label1)
                                .addGap(44, 44, 44))
                            .addGroup(GroupLayout.Alignment.TRAILING, MainBackgroundLayout.createSequentialGroup()
                                .addComponent(UsernameLabel)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(usernameErrorLabel, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                            .addGroup(GroupLayout.Alignment.TRAILING, MainBackgroundLayout.createParallelGroup()
                                .addGroup(MainBackgroundLayout.createSequentialGroup()
                                    .addComponent(PasswordLabel)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(passwordErrorLabel, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
                                    .addContainerGap())
                                .addGroup(GroupLayout.Alignment.TRAILING, MainBackgroundLayout.createSequentialGroup()
                                    .addGroup(MainBackgroundLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(scrollPane1, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(InputPassword, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(RegisterButton, GroupLayout.Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
                                    .addGap(85, 85, 85)))
                            .addGroup(GroupLayout.Alignment.TRAILING, MainBackgroundLayout.createSequentialGroup()
                                .addComponent(label2)
                                .addGap(111, 111, 111))))
            );
            MainBackgroundLayout.setVerticalGroup(
                MainBackgroundLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, MainBackgroundLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(label1)
                        .addGap(18, 18, 18)
                        .addGroup(MainBackgroundLayout.createParallelGroup()
                            .addComponent(UsernameLabel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                            .addComponent(usernameErrorLabel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(MainBackgroundLayout.createParallelGroup()
                            .addComponent(PasswordLabel, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                            .addComponent(passwordErrorLabel, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE))
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

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(MainBackground, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(MainBackground, GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

}
