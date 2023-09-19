package com.space.title;

import com.space.GameFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Title Screen for application
 * Launches game and hides this window on "Start Game" button
 */
public class TitleFrame extends JFrame {
    JFrame frame = new JFrame("Asteroids");

    public TitleFrame() {
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
        panel.add(button);

        JLabel titleImage = new JLabel(new ImageIcon("assets/images/TitleScreen.png"));
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
