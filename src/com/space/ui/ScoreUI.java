package com.space.ui;

import com.space.GamePanel;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ScoreUI {

    public static int score = 0;
    public static String displayText = "Score";
    private static final Rectangle rect = new Rectangle(GamePanel.GAME_WIDTH-240, 0, 240, 120);
    private static Font font;

    private ScoreUI() {
        // no-op
    }

    public static void addScore(int scoreGiven) {
        score += scoreGiven;
    }

    public static void draw(Graphics2D g) {
        int padding = 8;
        g.drawRect(rect.x-padding, rect.y+padding, rect.width, rect.height);
        g.setFont(font);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g.drawString(displayText, rect.x + (rect.width / 6), rect.y+50);
        g.drawString(Integer.toString(score), rect.x + (rect.width / 6), rect.y+100);
    }

    static {
        try {
            File fontFile = new File("assets/fonts/AtariFont.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            font = font.deriveFont(30f);
        }
        catch (IOException | FontFormatException e) {
            System.out.println(e.getLocalizedMessage());
            font = new Font("Arial", Font.PLAIN, 30);
        }

    }

}
