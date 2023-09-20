package com.space.frames;

import com.space.GameFrame;
import com.space.ui.FontLoader;

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

    public EndGameFrame() {

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);

        JPanel panel = new JPanel();
        frame.add(panel);

        panel.setLayout(null);

        JButton button = new JButton("Start Game");
        button.addActionListener(e -> loadGame());
        button.setLayout(null);
        button.setBounds(520, 400, 200, 50);
        //panel.add(button);

        JLabel scoreLabel = new JLabel("0");
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
        frame.setVisible(false);
        GameFrame game = new GameFrame();
        game.startGame();
    }

}
