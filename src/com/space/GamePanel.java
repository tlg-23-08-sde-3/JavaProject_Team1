package com.space;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    public static final int GAME_WIDTH = 1280;
    public static final int GAME_HEIGHT = 720;
    private static final Dimension SCREEN_RESOLUTION = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    private final int FPS = 60;

    PlayManager playManager = new PlayManager();
    Thread gameThread;


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
        }
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
