/*
 * Created by JFormDesigner on Tue Mar 16 19:59:15 IRST 2021
 */

package main.java.gui.Questions;

import main.java.config.FontConfig;
import main.java.config.ThemeConfig;
import main.java.database.Database;
import main.java.gui.Dashord.Dashboard;
import main.java.gui.GameOver.GameOver;
import main.java.models.Question;
import main.java.models.User;
import main.java.models.UserColumns;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author Alireza
 */

public class Questions extends JFrame {
	public final JFrame singlePlayer;
	JFrame CurrentFrame = this;
	User activeUser = Dashboard.activeUser;
	ArrayList<String> outAnswers = new ArrayList<>();
	ArrayList<JButton> outButtons = new ArrayList<>();
	ArrayList<Question> questions;
	ArrayList<Question> questions2;
	Question question;
	int seconds = 10;
	int score = 0;
	String recordColumn;
	int category;
	
	public Questions(JFrame singlePlayer, ArrayList<Question> questions,String recordColumn,int category) {
		this.recordColumn = recordColumn;
		this.category=category;
		this.questions = questions;
		this.singlePlayer = singlePlayer;
		questions2 = new ArrayList<>(questions);
		initComponents();
		init();
		initCustomTheme();
		this.setVisible(true);
	}

	private void initCustomTheme(){
		Panel.setBackground(ThemeConfig.background);
		for (JButton jButton : Arrays.asList(answerButton1,answerButton2,answerButton3,answerButton4)){
			jButton.setBackground(ThemeConfig.button);
		}
	}
	
	Timer countdown = new Timer(1000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			timelabel.setText(String.valueOf(seconds));
			seconds--;
			timeProgressBar.setValue(10 - seconds);
			if (seconds > 6) timeProgressBar.setBackground(Color.green);
			else if (seconds > 3) timeProgressBar.setBackground(Color.yellow);
			else if (seconds > 0) timeProgressBar.setBackground(Color.red);
			else {
				CurrentFrame.dispose();
				new GameOver(singlePlayer, score, category);
				countdown.stop();
			}
		}
	});
	
	private void init() {
		coinAmountLabel.setText(String.valueOf(activeUser.coins));
		timeProgressBar.setMaximum(10);
		timeProgressBar.setMinimum(0);
		currentScoreLable.setText(String.valueOf(score));
		initComponentsProperties();
		showQuestion(questions2);
		outButtons.add(answerButton1);
		outButtons.add(answerButton2);
		outButtons.add(answerButton3);
		outButtons.add(answerButton4);
	}
	
	private void showQuestion(ArrayList<Question> questions2) {
		timeProgressBar.setValue(0);
		countdown.start();
		for (JButton button : outButtons) {
			button.setVisible(true);
		}
		outAnswers.clear();
		question = randomQuestion(questions2);
		if (question != null) {
			questionLabel.setText(question.question);
			ArrayList<String> answers = new ArrayList<>();
			answers.add(question.answer1);
			outAnswers.add(question.answer1);
			answers.add(question.answer2);
			outAnswers.add(question.answer2);
			answers.add(question.answer3);
			outAnswers.add(question.answer3);
			answers.add(question.correctAnswer);
			outAnswers.remove(randomAnswer(outAnswers));
			for (JButton jButton :Arrays.asList(answerButton1,answerButton2,answerButton3,answerButton4)){
				jButton.setText(randomAnswer(answers));
			}
		} else {
			countdown.stop();
			CurrentFrame.dispose();
			new GameOver(singlePlayer, score, category);
		}
	}
	
	private String randomAnswer(ArrayList<String> answers) {
		Random random = new Random();
		int rand = random.nextInt(answers.size());
		String answer = answers.get(rand);
		answers.remove(rand);
		return answer;
	}
	
	private Question randomQuestion(ArrayList<Question> questions2) {
		if (questions2.size() > 0) {
			Random random = new Random();
			int rand = random.nextInt(questions2.size());
			Question question = questions2.get(rand);
			questions2.remove(rand);
			return question;
		} else {
			return null;
		}
	}
	
	private void questionsWindowClosing(WindowEvent e) {
		this.dispose();
		countdown.stop();
		new GameOver(singlePlayer, score, category);
	}
	
	private void isCorrect(JButton inputAnswer) {
		if (inputAnswer.getText().equals(question.correctAnswer)) {
			countdown.stop();
			score++;
			updateUserRecord();
			currentScoreLable.setText(String.valueOf(score));
			Dashboard.activeUser.coins += 20;
			Database.updateDatabaseUserCoins(activeUser.username, activeUser.coins);
			coinAmountLabel.setText(String.valueOf(activeUser.coins));
			inputAnswer.setBackground(Color.GREEN);
			inputAnswer.setForeground(Color.BLACK);
			
			Timer pause = new Timer(500, e -> {
				inputAnswer.setBackground(ThemeConfig.button);
				inputAnswer.setForeground(Color.white);
				seconds = 10;
				showQuestion(questions2);
			});
			pause.setRepeats(false);
			pause.start();
		} else {
			inputAnswer.setBackground(Color.RED);
			inputAnswer.setForeground(Color.BLACK);
			countdown.stop();
			
			Timer pause = new Timer(500, e -> {
				inputAnswer.setBackground(ThemeConfig.button);
				inputAnswer.setForeground(Color.white);
				seconds = 10;
				CurrentFrame.dispose();
				new GameOver(singlePlayer, score, category);
			});
			pause.setRepeats(false);
			pause.start();
		}
	}
	
	private void updateUserRecord() {
		Database.updateDatabaseUserRecord(activeUser.username, recordColumn, score);
		switch (category) {
			case 1 -> {
				if (activeUser.recordEnglish < score) activeUser.recordEnglish = score;
			}
			case 2 -> {
				if (activeUser.recordMath < score) activeUser.recordMath = score;
			}
			case 3 -> {
				if (activeUser.recordFood < score) activeUser.recordFood = score;
			}
			case 4 -> {
				if (activeUser.recordScience < score) activeUser.recordScience = score;
			}
			case 5 -> {
				if (activeUser.recordCommon < score) activeUser.recordCommon = score;
			}
			default -> {
				if (activeUser.recordGeography < score) activeUser.recordGeography = score;
			}
		}
	}
	
	private void initComponentsProperties() {
		for (JButton jButton : Arrays.asList(answerButton1,answerButton2,answerButton3,
				answerButton4,Freezer,Helper)){
			jButton.setFont(FontConfig.comic.deriveFont(Font.PLAIN, 15));
		}
		questionLabel.setFont(FontConfig.comic.deriveFont(Font.BOLD, 18));
	}
	
	private void answerButton3ActionPerformed(ActionEvent e) {
		isCorrect(answerButton3);
	}
	
	private void answerButton2ActionPerformed(ActionEvent e) {
		isCorrect(answerButton2);
	}
	
	private void answerButton4ActionPerformed(ActionEvent e) {
		isCorrect(answerButton4);
	}
	
	private void answerButton1ActionPerformed(ActionEvent e) {
		isCorrect(answerButton1);
	}
	
	private void FreezerActionPerformed(ActionEvent e) {
		Freezer.setBackground(new Color(200, 10, 50));
		Freezer.setForeground(Color.black);
		Freezer.setEnabled(false);
		if (activeUser.coins >= 100) {
			activeUser.coins -= 100;
			Database.updateDatabaseUserCoins(activeUser.username, activeUser.coins);
			coinAmountLabel.setText(String.valueOf(activeUser.coins));
			countdown.stop();
			Freezer.setText("Froze");
			Timer delay = new Timer(5000, e1 -> {
				Freezer.setEnabled(true);
				Freezer.setText("<html>Freeze Time<br>&nbsp;&nbsp;&nbsp;100 coins</html>");
				Freezer.setBackground(new Color(0, 153, 51));
				Freezer.setForeground(Color.white);
				countdown.restart();
			});
			delay.setRepeats(false);
			delay.start();
		} else {
			Freezer.setText("Not Enough Coin");
			Timer delay = new Timer(2000, e12 -> {
				Freezer.setEnabled(true);
				Freezer.setText("<html>Freeze Time<br>&nbsp;&nbsp;&nbsp;100 coins</html>");
				Freezer.setBackground(new Color(0, 153, 51));
				Freezer.setForeground(Color.white);
			});
			delay.setRepeats(false);
			delay.start();
		}
	}
	
	private void HelperActionPerformed(ActionEvent e) {
		Helper.setBackground(new Color(200, 10, 50));
		Helper.setForeground(Color.black);
		Helper.setEnabled(false);
		if (activeUser.coins >= 200) {
			activeUser.coins -= 200;
			Database.updateDatabaseUserCoins(activeUser.username, activeUser.coins);
			coinAmountLabel.setText(String.valueOf(activeUser.coins));
			Helper.setText("Out !");
			String firstOut = outAnswers.get(0);
			String secondOut = outAnswers.get(1);
			for (JButton b : outButtons) {
				if (firstOut.equals(b.getText())) {
					b.setVisible(false);
				}
				if (secondOut.equals(b.getText())) {
					b.setVisible(false);
				}
			}
			
			Timer delay = new Timer(2000, e1 -> {
				Helper.setEnabled(true);
				Helper.setText("<html>2 Wrongs Out<br>&nbsp;&nbsp;&nbsp;200 coins</html>");
				Helper.setBackground(new Color(0, 153, 51));
				Helper.setForeground(Color.white);
			});
			delay.setRepeats(false);
			delay.start();
		} else {
			Helper.setText("Not Enough Coin");
			Timer delay = new Timer(2000, e12 -> {
				Helper.setEnabled(true);
				Helper.setText("<html>2 Wrongs Out<br>&nbsp;&nbsp;&nbsp;200 coins</html>");
				Helper.setBackground(new Color(0, 153, 51));
				Helper.setForeground(Color.white);
			});
			delay.setRepeats(false);
			delay.start();
		}
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
        label1 = new JLabel();
        currentScoreLable = new JLabel();
        timelabel = new JLabel();
        timeProgressBar = new JProgressBar();

        //======== this ========
        setMinimumSize(new Dimension(380, 605));
        setName("Questions");
        setMaximizedBounds(new Rectangle(580, 60, 380, 605));
        setResizable(false);
        setTitle("Questions");
        setBackground(new Color(0, 112, 192));
        setIconImage(new ImageIcon(getClass().getResource("/main/resources/icons/Theme/Logo (1).jpg")).getImage());
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

            //---- questionLabel ----
            questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
            questionLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 18));
            questionLabel.setForeground(Color.white);
            questionLabel.setBackground(new Color(0, 0, 204));

            //---- Freezer ----
            Freezer.setText("<html>Freeze Time<br>&nbsp;&nbsp;&nbsp;100 coins </html>");
            Freezer.setBackground(new Color(0, 153, 51));
            Freezer.setFocusable(false);
            Freezer.setForeground(Color.white);
            Freezer.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
            Freezer.setBorder(null);
            Freezer.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Freezer.addActionListener(e -> FreezerActionPerformed(e));

            //---- Helper ----
            Helper.setText("<html>2 Wrongs Out<br>&nbsp;&nbsp;&nbsp;200 coins</html>");
            Helper.setBackground(new Color(0, 153, 51));
            Helper.setFocusable(false);
            Helper.setForeground(Color.white);
            Helper.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
            Helper.setBorder(null);
            Helper.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            Helper.addActionListener(e -> HelperActionPerformed(e));

            //---- answerButton3 ----
            answerButton3.setBackground(new Color(0, 32, 96));
            answerButton3.setFocusable(false);
            answerButton3.setForeground(Color.white);
            answerButton3.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
            answerButton3.setBorder(null);
            answerButton3.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            answerButton3.addActionListener(e -> answerButton3ActionPerformed(e));

            //---- answerButton2 ----
            answerButton2.setBackground(new Color(0, 32, 96));
            answerButton2.setFocusable(false);
            answerButton2.setForeground(Color.white);
            answerButton2.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
            answerButton2.setBorder(null);
            answerButton2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            answerButton2.addActionListener(e -> answerButton2ActionPerformed(e));

            //---- answerButton4 ----
            answerButton4.setBackground(new Color(0, 32, 96));
            answerButton4.setFocusable(false);
            answerButton4.setForeground(Color.white);
            answerButton4.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
            answerButton4.setBorder(null);
            answerButton4.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            answerButton4.addActionListener(e -> answerButton4ActionPerformed(e));

            //---- answerButton1 ----
            answerButton1.setBackground(new Color(0, 32, 96));
            answerButton1.setFocusable(false);
            answerButton1.setForeground(Color.white);
            answerButton1.setFont(new Font("Comic Sans MS", Font.PLAIN, 15));
            answerButton1.setBorder(null);
            answerButton1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            answerButton1.addActionListener(e -> answerButton1ActionPerformed(e));

            //---- coinLabel ----
            coinLabel.setIcon(new ImageIcon(getClass().getResource("/main/resources/icons/Dashboard/smallCoin.png")));

            //---- coinAmountLabel ----
            coinAmountLabel.setHorizontalAlignment(SwingConstants.CENTER);
            coinAmountLabel.setForeground(new Color(255, 255, 51));

            //---- label1 ----
            label1.setText("Score :");
            label1.setForeground(Color.white);

            //---- currentScoreLable ----
            currentScoreLable.setForeground(Color.white);

            //---- timelabel ----
            timelabel.setBackground(new Color(255, 153, 0));
            timelabel.setForeground(new Color(255, 153, 0));
            timelabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));
            timelabel.setText("10");
            timelabel.setHorizontalAlignment(SwingConstants.CENTER);

            //---- timeProgressBar ----
            timeProgressBar.setBackground(Color.green);

            GroupLayout PanelLayout = new GroupLayout(Panel);
            Panel.setLayout(PanelLayout);
            PanelLayout.setHorizontalGroup(
                PanelLayout.createParallelGroup()
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PanelLayout.createParallelGroup()
                            .addGroup(PanelLayout.createSequentialGroup()
                                .addGroup(PanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addGroup(PanelLayout.createSequentialGroup()
                                        .addGap(0, 19, Short.MAX_VALUE)
                                        .addGroup(PanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                            .addComponent(answerButton3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                            .addComponent(answerButton4, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                            .addComponent(Freezer, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                                        .addGap(27, 27, 27)
                                        .addGroup(PanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addComponent(answerButton1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(answerButton2, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(Helper, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(PanelLayout.createSequentialGroup()
                                        .addGroup(PanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                            .addGroup(PanelLayout.createSequentialGroup()
                                                .addGap(6, 6, 6)
                                                .addComponent(timelabel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(timeProgressBar, GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE))
                                            .addGroup(PanelLayout.createSequentialGroup()
                                                .addComponent(label1)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(currentScoreLable, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(coinAmountLabel, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(coinLabel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
                                .addGap(26, 26, 26))
                            .addGroup(GroupLayout.Alignment.TRAILING, PanelLayout.createSequentialGroup()
                                .addComponent(questionLabel, GroupLayout.DEFAULT_SIZE, 366, Short.MAX_VALUE)
                                .addContainerGap())))
            );
            PanelLayout.setVerticalGroup(
                PanelLayout.createParallelGroup()
                    .addGroup(PanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(PanelLayout.createParallelGroup()
                            .addGroup(PanelLayout.createSequentialGroup()
                                .addComponent(currentScoreLable, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(4, 4, 4))
                            .addComponent(label1, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
                            .addComponent(coinLabel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
                            .addComponent(coinAmountLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PanelLayout.createParallelGroup()
                            .addComponent(timelabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(PanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(timeProgressBar, GroupLayout.PREFERRED_SIZE, 13, GroupLayout.PREFERRED_SIZE)))
                        .addGap(1, 1, 1)
                        .addComponent(questionLabel, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(PanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(answerButton3, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
                            .addComponent(answerButton2, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(PanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(answerButton4, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
                            .addComponent(answerButton1, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37)
                        .addGroup(PanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addComponent(Freezer, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
                            .addComponent(Helper, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
                        .addGap(35, 35, 35))
            );
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(Panel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addComponent(Panel, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE)
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
    private JLabel label1;
    private JLabel currentScoreLable;
    private JLabel timelabel;
    private JProgressBar timeProgressBar;
	// JFormDesigner - End of variables declaration  //GEN-END:variables
}
