/*
 * Created by JFormDesigner on Mon May 24 15:55:56 IRDT 2021
 */

package main.java.gui.MultiplayerQuestion;

import main.java.config.FontConfig;
import main.java.config.GuiConfig;
import main.java.config.ProfileConfig;
import main.java.config.ThemeConfig;
import main.java.gui.Dashboard.Dashboard;
import main.java.gui.MultiplayerQuestion.GameOver.GameOver;
import main.java.models.User;
import main.java.socket.Client;
import main.java.socket.ReceiveCategoryName;
import main.java.socket.Requests;
import main.java.socket.UpdateScores;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import javax.swing.*;

/**
 * @author Alireza
 */
public class MultiplayerQuestion extends JFrame {
    int seconds = 10;
    static int questionNumber;
    User activeUser;
    //  ClockAnimation clockAnimationSlow;
    Client CLIENT;
    JFrame currentFrame;
    static ArrayList<JLabel> labelsLeft = new ArrayList<>();
    static ArrayList<JLabel> labelsRight = new ArrayList<>();
    JButton selectedButton;
    UpdateScores updateScores;
    JFrame dashboard;

    public MultiplayerQuestion(Client client, JFrame dashboard) {
        this.dashboard = dashboard;
        questionNumber = 0;
        currentFrame = this;
        activeUser = Dashboard.activeUser;
        initComponents();
        init();
        // clockAnimationSlow = new ClockAnimation(clockLabel);
        this.CLIENT = client;
        initListeners();
        this.setVisible(true);
        initCircleLabels();
        setUsers();
        updateScores = new UpdateScores(CLIENT, this);
        updateScores.start();
        receiveQuestion();
    }

    private void setUsers() {
        CLIENT.sendRequest(Requests.SET_USER.request);

        userNameLabel1.setText(CLIENT.getResponse());
        profile1.setIcon(new ImageIcon(Objects.requireNonNull(getClass()
                .getResource(ProfileConfig.profilePicture(CLIENT.getResponseInt())))));

        userNameLabel2.setText(CLIENT.getResponse());
        profile2.setIcon(new ImageIcon(Objects.requireNonNull(getClass()
                .getResource(ProfileConfig.profilePicture(CLIENT.getResponseInt())))));
    }

    private void receiveQuestion() {
        CLIENT.sendRequest(Requests.GET_QUESTION.request);
        CLIENT.sendRequest(String.valueOf(questionNumber));

        questionLabel.setText(CLIENT.getResponse());
        for (JButton jButton : Arrays.asList(answerButton1, answerButton2,
                answerButton3, answerButton4))
            jButton.setText(CLIENT.getResponse());
    }

    private void initCustomTheme() {
        Panel.setBackground(ThemeConfig.background);
        for (JButton jButton : Arrays.asList(answerButton1, answerButton2,
                answerButton3, answerButton4)) {
            jButton.setBackground(ThemeConfig.button);
        }
    }

    private void initComponentsProperties() {
        init1(answerButton1, answerButton2, answerButton3, answerButton4,
                Freezer, Helper, questionLabel, coinAmountLabel);

        userNameLabel2.setFont(FontConfig.comic.deriveFont(Font.PLAIN, 16));
        userNameLabel1.setFont(FontConfig.comic.deriveFont(Font.PLAIN, 16));
    }

    public static void init1(JButton answerButton1, JButton answerButton2, JButton answerButton3,
                             JButton answerButton4, JButton freezer, JButton helper,
                             JLabel questionLabel, JLabel coinAmountLabel) {
        for (JButton jButton : Arrays.asList(answerButton1, answerButton2, answerButton3,
                answerButton4, freezer, helper))
            jButton.setFont(FontConfig.comic.deriveFont(Font.PLAIN, 15));

        questionLabel.setFont(FontConfig.comic.deriveFont(Font.BOLD, 18));
        coinAmountLabel.setFont(FontConfig.comic.deriveFont(Font.BOLD, 19));
    }

    private void init() {
        initCustomTheme();
        coinAmountLabel.setText(String.valueOf(activeUser.coins));
        timeProgressBar.setMaximum(10);
        timeProgressBar.setMinimum(0);
        initComponentsProperties();
    }

    public void initCircleLabels() {
        labelsLeft.addAll(Arrays.asList(icon1, icon2, icon3, icon4, icon5));
        labelsRight.addAll(Arrays.asList(icon10, icon9, icon8, icon7, icon6));
    }

    private void initListeners() {
        for (JButton jButton : Arrays.asList(
                answerButton1, answerButton2, answerButton3, answerButton4))
            jButton.addActionListener(e -> checkTheAnswer(jButton));
    }

    public void updateScore(String username, String isTrue, int questionNumberServer) {
        if (username.equals(userNameLabel1.getText()))
            greenOrRed(labelsLeft, isTrue, questionNumberServer);
        else
            greenOrRed(labelsRight, isTrue, questionNumberServer);

        if (username.equals(activeUser.username)) {
            if (isTrue.equals(Requests.ACCEPT.request))
                updateInClient(GuiConfig.COLOR_GREEN, 20);

            else
                updateInClient(GuiConfig.COLOR_DARK_RED, 0);
        }
    }

    private void updateInClient(Color color, int coins) {
        activeUser.coins += coins;
        updateCoins();
        selectedButton.setBackground(color);
        Timer pause = new Timer(500, e -> {
            selectedButton.setBackground(ThemeConfig.button);
            seconds = 10;
            questionNumber++;

            if (questionNumber == 5) {
                UpdateScores.isFinished = true;
                this.dispose();
                new GameOver(CLIENT, dashboard);
            } else
                receiveQuestion();
        });
        pause.setRepeats(false);
        pause.start();
    }

    private void greenOrRed(ArrayList<JLabel> jLabels, String isTrue, int questionNumberServer) {
        jLabels.get(questionNumberServer).setIcon(new ImageIcon(Objects.requireNonNull(
                MultiplayerQuestion.class.getResource("/main/resources/icons/Multiplayer/Questions/" + isTrue + "-button.png"))));
    }

    private void checkTheAnswer(JButton button) {
        CLIENT.sendRequest(Requests.IS_CORRECT.request);
        CLIENT.sendRequest(activeUser.username);
        CLIENT.sendRequest(String.valueOf(questionNumber));
        CLIENT.sendRequest(button.getText());

        selectedButton = button;
    }


    private void updateCoins() {
        CLIENT.sendRequest("UPDATE_COINS");
        CLIENT.sendRequest(activeUser.username);
        CLIENT.sendRequest(String.valueOf(activeUser.coins));

        coinAmountLabel.setText(String.valueOf(activeUser.coins));
    }

    private void questionsWindowClosing(WindowEvent e) {

    }

    private void FreezerActionPerformed(ActionEvent e) {

    }

    private void HelperActionPerformed(ActionEvent e) {

    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        Panel = new JPanel();
        questionLabel = new JLabel();
        Freezer = new JButton();
        Helper = new JButton();
        answerButton3 = new JButton();
        answerButton2 = new JButton();
        answerButton4 = new JButton();
        answerButton1 = new JButton();
        coinLabel = new JLabel();
        coinAmountLabel = new JLabel();
        timelabel = new JLabel();
        timeProgressBar = new JProgressBar();
        clockLabel = new JLabel();
        icon1 = new JLabel();
        icon2 = new JLabel();
        icon3 = new JLabel();
        icon4 = new JLabel();
        icon5 = new JLabel();
        icon6 = new JLabel();
        icon7 = new JLabel();
        icon8 = new JLabel();
        icon9 = new JLabel();
        icon10 = new JLabel();
        profile1 = new JLabel();
        profile2 = new JLabel();
        userNameLabel1 = new JLabel();
        userNameLabel2 = new JLabel();

        //======== this ========
        setMinimumSize(new Dimension(380, 605));
        setName("Questions");
        setMaximizedBounds(new Rectangle(580, 60, 380, 605));
        setResizable(false);
        setTitle("Questions");
        setBackground(new Color(0, 112, 192));
        setIconImage(new ImageIcon(getClass().getResource("/main/resources/icons/Theme/Logo.jpg")).getImage());
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                questionsWindowClosing(e);
            }
        });
        var contentPane = getContentPane();

        //======== Panel ========
        {
            Panel.setBackground(new Color(0, 112, 192));
            Panel.setLayout(null);

            //---- questionLabel ----
            questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
            questionLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
            questionLabel.setForeground(Color.white);
            questionLabel.setBackground(new Color(0, 0, 204));
            Panel.add(questionLabel);
            questionLabel.setBounds(5, 210, 366, 127);

            //---- Freezer ----
            Freezer.setText("<html>Freeze Time<br>&nbsp;&nbsp;&nbsp;100 coins </html>");
            Freezer.setBackground(new Color(0, 153, 51));
            Freezer.setFocusable(false);
            Freezer.setForeground(Color.white);
            Freezer.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
            Freezer.setBorder(null);
            Freezer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Freezer.addActionListener(e -> FreezerActionPerformed(e));
            Panel.add(Freezer);
            Freezer.setBounds(25, 525, 150, 53);

            //---- Helper ----
            Helper.setText("<html>2 Wrongs Out<br>&nbsp;&nbsp;&nbsp;200 coins</html>");
            Helper.setBackground(new Color(0, 153, 51));
            Helper.setForeground(Color.white);
            Helper.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
            Helper.setBorder(null);
            Helper.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Helper.setFocusable(false);
            Helper.addActionListener(e -> HelperActionPerformed(e));
            Panel.add(Helper);
            Helper.setBounds(200, 525, 150, 53);

            //---- answerButton3 ----
            answerButton3.setBackground(new Color(0, 32, 96));
            answerButton3.setFocusable(false);
            answerButton3.setForeground(Color.white);
            answerButton3.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
            answerButton3.setBorder(null);
            answerButton3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Panel.add(answerButton3);
            answerButton3.setBounds(25, 350, 150, 76);

            //---- answerButton2 ----
            answerButton2.setBackground(new Color(0, 32, 96));
            answerButton2.setFocusable(false);
            answerButton2.setForeground(Color.white);
            answerButton2.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
            answerButton2.setBorder(null);
            answerButton2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Panel.add(answerButton2);
            answerButton2.setBounds(205, 350, 150, 76);

            //---- answerButton4 ----
            answerButton4.setBackground(new Color(0, 32, 96));
            answerButton4.setFocusable(false);
            answerButton4.setForeground(Color.white);
            answerButton4.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
            answerButton4.setBorder(null);
            answerButton4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Panel.add(answerButton4);
            answerButton4.setBounds(25, 435, 150, 76);

            //---- answerButton1 ----
            answerButton1.setBackground(new Color(0, 32, 96));
            answerButton1.setFocusable(false);
            answerButton1.setForeground(Color.white);
            answerButton1.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
            answerButton1.setBorder(null);
            answerButton1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Panel.add(answerButton1);
            answerButton1.setBounds(205, 435, 150, 76);

            //---- coinLabel ----
            coinLabel.setIcon(new ImageIcon(getClass().getResource("/main/resources/icons/Dashboard/smallCoin.png")));
            Panel.add(coinLabel);
            coinLabel.setBounds(20, 15, 35, 37);

            //---- coinAmountLabel ----
            coinAmountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            coinAmountLabel.setForeground(new Color(255, 255, 51));
            Panel.add(coinAmountLabel);
            coinAmountLabel.setBounds(60, 10, 57, 43);

            //---- timelabel ----
            timelabel.setBackground(new Color(255, 153, 0));
            timelabel.setForeground(new Color(255, 153, 0));
            timelabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 27));
            timelabel.setText("10");
            timelabel.setHorizontalAlignment(SwingConstants.CENTER);
            Panel.add(timelabel);
            timelabel.setBounds(45, 45, 55, 40);

            //---- timeProgressBar ----
            timeProgressBar.setBackground(Color.green);
            Panel.add(timeProgressBar);
            timeProgressBar.setBounds(95, 60, 220, 18);
            Panel.add(clockLabel);
            clockLabel.setBounds(320, 30, 50, 44);

            //---- icon1 ----
            icon1.setIcon(null);
            icon1.setHorizontalAlignment(SwingConstants.CENTER);
            Panel.add(icon1);
            icon1.setBounds(60, 110, 25, 35);

            //---- icon2 ----
            icon2.setHorizontalAlignment(SwingConstants.CENTER);
            Panel.add(icon2);
            icon2.setBounds(85, 110, 25, 35);

            //---- icon3 ----
            icon3.setIcon(null);
            icon3.setHorizontalAlignment(SwingConstants.CENTER);
            Panel.add(icon3);
            icon3.setBounds(110, 110, 25, 35);

            //---- icon4 ----
            icon4.setIcon(null);
            icon4.setHorizontalAlignment(SwingConstants.CENTER);
            Panel.add(icon4);
            icon4.setBounds(135, 110, 25, 35);

            //---- icon5 ----
            icon5.setIcon(null);
            icon5.setHorizontalAlignment(SwingConstants.CENTER);
            Panel.add(icon5);
            icon5.setBounds(160, 110, 25, 35);

            //---- icon6 ----
            icon6.setIcon(null);
            icon6.setHorizontalAlignment(SwingConstants.CENTER);
            Panel.add(icon6);
            icon6.setBounds(195, 110, 25, 35);

            //---- icon7 ----
            icon7.setIcon(null);
            icon7.setHorizontalAlignment(SwingConstants.CENTER);
            Panel.add(icon7);
            icon7.setBounds(220, 110, 25, 35);

            //---- icon8 ----
            icon8.setIcon(null);
            icon8.setHorizontalAlignment(SwingConstants.CENTER);
            Panel.add(icon8);
            icon8.setBounds(245, 110, 25, 35);

            //---- icon9 ----
            icon9.setIcon(null);
            icon9.setHorizontalAlignment(SwingConstants.CENTER);
            Panel.add(icon9);
            icon9.setBounds(270, 110, 25, 35);

            //---- icon10 ----
            icon10.setIcon(null);
            icon10.setHorizontalAlignment(SwingConstants.CENTER);
            Panel.add(icon10);
            icon10.setBounds(295, 110, 25, 35);
            Panel.add(profile1);
            profile1.setBounds(5, 100, 55, 55);
            Panel.add(profile2);
            profile2.setBounds(320, 100, 55, 55);

            //---- userNameLabel1 ----
            userNameLabel1.setForeground(Color.white);
            userNameLabel1.setFont(userNameLabel1.getFont().deriveFont(userNameLabel1.getFont().getSize() + 4f));
            Panel.add(userNameLabel1);
            userNameLabel1.setBounds(20, 155, 165, 40);

            //---- userNameLabel2 ----
            userNameLabel2.setHorizontalAlignment(SwingConstants.RIGHT);
            userNameLabel2.setForeground(Color.white);
            Panel.add(userNameLabel2);
            userNameLabel2.setBounds(195, 155, 165, 40);

            {
                // compute preferred size
                Dimension preferredSize = new Dimension();
                for (int i = 0; i < Panel.getComponentCount(); i++) {
                    Rectangle bounds = Panel.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = Panel.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                Panel.setMinimumSize(preferredSize);
                Panel.setPreferredSize(preferredSize);
            }
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
                contentPaneLayout.createParallelGroup()
                        .addComponent(Panel, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
                contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                                .addComponent(Panel, GroupLayout.PREFERRED_SIZE, 611, GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel Panel;
    private JLabel questionLabel;
    private JButton Freezer;
    private JButton Helper;
    private JButton answerButton3;
    private JButton answerButton2;
    private JButton answerButton4;
    private JButton answerButton1;
    private JLabel coinLabel;
    private JLabel coinAmountLabel;
    private JLabel timelabel;
    private JProgressBar timeProgressBar;
    private JLabel clockLabel;
    private JLabel icon1;
    private JLabel icon2;
    private JLabel icon3;
    private JLabel icon4;
    private JLabel icon5;
    private JLabel icon6;
    private JLabel icon7;
    private JLabel icon8;
    private JLabel icon9;
    private JLabel icon10;
    private JLabel profile1;
    private JLabel profile2;
    private JLabel userNameLabel1;
    private JLabel userNameLabel2;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
