package com.space.ui;

import com.space.GamePanel;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ScoreUI {

    public static int score = 0;
    public static String displayText = "Score";
    private static final Rectangle rect = new Rectangle(GamePanel.GAME_WIDTH - 180, 0, 180, 100);
    private static final Font font = FontLoader.loadFont("assets/fonts/AtariFont.ttf", 22f);

    private ScoreUI() {
        // no-op
    }

    public static void addScore(int scoreGiven) {
        score += scoreGiven;
    }

    public static void draw(Graphics2D g) {
        int padding = 8;
        g.drawRect(rect.x - padding, rect.y + padding, rect.width, rect.height);
        g.setFont(font);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g.drawString(displayText, rect.x + (rect.width / 6), rect.y + 50);
        g.drawString(Integer.toString(score), rect.x + (rect.width / 6), rect.y + 90);
    }

}
