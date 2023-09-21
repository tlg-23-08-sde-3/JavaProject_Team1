package com.space.logic;

import com.space.audio.Audio;
import com.space.frames.EndGameFrame;
import com.space.frames.GameFrame;
import com.space.frames.ResetGame;
import com.space.ui.ScoreUI;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    // static fields
    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;
    private static final Dimension SCREEN_RESOLUTION = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    public static boolean isRunning = true;

    // fields
    private final int FPS = 60;
    private PlayManager playManager = new PlayManager();
    private Thread gameThread;

    // constructors
    public GamePanel() {
        this.setFocusable(true);
        this.setPreferredSize(SCREEN_RESOLUTION);
        this.setBackground(Color.BLACK);
        this.addKeyListener(new KeyHandler());
    }

    // Start the game thread
    public void startGame() {
        gameThread = new Thread(this);
        gameThread.start();
        ResetGame.touch();
        Audio.loopSound(3);
    }

    @Override
    public void run() {
        // Generic Game Loop
        double drawInterval = 1_000_000_000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            // Should run this if statement at the FPS (currently 60 times per second)
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
            if (!isRunning) {
                endGame();
            }
        }
    }

    private void endGame() {
        gameThread = null;
        Audio.stopSound(3);
        System.out.println("Game over");
        this.setVisible(false);
        GameFrame.disposeGame();
        EndGameFrame egf = new EndGameFrame(ScoreUI.score);
        playManager = new PlayManager();
    }

    private void update() {
        playManager.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        playManager.draw(g2d);
    }
}
