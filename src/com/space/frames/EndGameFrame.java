package com.space.frames;

import com.space.audio.Audio;
import com.space.ui.FontLoader;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;

public class EndGameFrame extends JFrame {

    // fields
    private final JFrame frame = new JFrame("Asteroids");
    private final String fontString = "assets/fonts/AtariFont.ttf";
    private final float fontSize = 30f;
    private final Font font = FontLoader.loadFont(fontString, fontSize);
    private boolean isHighScore = false;

    // constructors
    public EndGameFrame(int score) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);

        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(null);

        // Restart Game Button
        JButton startGameButton = new JButton("Play Again!");
        startGameButton.addActionListener(e -> loadGame());
        startGameButton.setLayout(null);
        startGameButton.setBounds(1020, 300, 200, 50);
        panel.add(startGameButton);

        // Close Game Button
        JButton closeButton = new JButton("Quit");
        closeButton.addActionListener(e -> closeGame());
        closeButton.setLayout(null);
        closeButton.setBounds(1020, 400, 200, 50);
        panel.add(closeButton);

        // Last game score
        JLabel scoreLabel = new JLabel(Integer.toString(score));
        scoreLabel.setFont(font);
        scoreLabel.setBounds(380, 233, 200, 200);
        scoreLabel.setForeground(Color.WHITE);
        panel.add(scoreLabel);

        // High Score
        int highScore = getHighScore();
        JLabel highScoreLabel = new JLabel(Integer.toString(highScore));
        highScoreLabel.setFont(font);
        highScoreLabel.setBounds(380, 333, 200, 200);
        highScoreLabel.setForeground(Color.WHITE);
        panel.add(highScoreLabel);

        // Check for new high score
        saveHighScore(score, highScore);
        if (isHighScore) {
            JLabel newHighScoreLabel = new JLabel("New HighScore!");
            newHighScoreLabel.setFont(font);
            newHighScoreLabel.setBounds(50, 133, 700, 200);
            newHighScoreLabel.setForeground(Color.WHITE);
            panel.add(newHighScoreLabel);
        }

        JLabel titleImage = new JLabel(new ImageIcon("assets/images/EndScreen.png"));
        panel.add(titleImage);
        titleImage.setBounds(0, 0, 1280, 720);

        JLabel titleLabel = new JLabel("Welcome to Asteroids");
        panel.add(titleLabel);

        frame.setVisible(true);

        Audio.playSound(4);
    }

    // action methods
    private void saveHighScore(int score, int highScore) {
        if (score > highScore) {
            SaveHandler.save(score);
            isHighScore = true;
        }
    }

    private int getHighScore() {
        return SaveHandler.load();
    }

    private void loadGame() {
        Audio.stopSound(4);
        ResetGame.reset();
        frame.dispose();
    }

    private void closeGame() {
        System.exit(0);
    }
}
