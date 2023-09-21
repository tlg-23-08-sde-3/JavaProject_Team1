package com.space.frames;

import com.space.logic.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GameFrame {

    private static JFrame frame;
    private GamePanel panel;

    public GameFrame() {
        frame = new JFrame();
        panel = new GamePanel();
        frame.add(panel);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Asteroids");
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
        frame.pack();
    }

    public void startGame() {
        panel.startGame();
    }

    public static void disposeGame() {
        frame.dispose();
    }


}
