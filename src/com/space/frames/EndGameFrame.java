package com.space.frames;

import com.space.GameFrame;
import com.space.GamePanel;
import com.space.ui.FontLoader;
import com.space.ui.HealthUI;
import com.space.ui.ScoreUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class EndGameFrame extends JFrame {

    JFrame frame = new JFrame("Asteroids");
    String fontString = "assets/fonts/AtariFont.ttf";
    float fontSize = 30f;
    Font font = FontLoader.loadFont(fontString, fontSize);

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
        startGameButton.setBounds(1020, 400, 200, 50);
        panel.add(startGameButton);

        // Close Game Button
        JButton closeButton = new JButton("Quit");
        closeButton.addActionListener(e -> closeGame());
        closeButton.setLayout(null);
        closeButton.setBounds(1020, 500, 200, 50);
        panel.add(closeButton);

        // Last game score
        JLabel scoreLabel = new JLabel(Integer.toString(score));
        scoreLabel.setFont(font);
        scoreLabel.setBounds(380, 233, 200, 200);
        scoreLabel.setForeground(Color.WHITE);
        panel.add(scoreLabel);

        JLabel titleImage = new JLabel(new ImageIcon("assets/images/EndScreen.png"));
        panel.add(titleImage);
        titleImage.setBounds(0, 0, 1280, 720);

        JLabel titleLabel = new JLabel("Welcome to Asteroids");
        panel.add(titleLabel);


        frame.setVisible(true);
    }

    private void loadGame() {

        ResetGame.reset();
        frame.dispose();
    }

    private void closeGame() {
        System.exit(0);
    }

}
