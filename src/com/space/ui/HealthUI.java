package com.space.ui;

import com.space.GamePanel;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HealthUI {

    public static int life = 3;
    public static String displayText = "Lives";
    private static final Rectangle rect = new Rectangle(0, 0, 240, 120);
    private static Font font;

    private HealthUI() {
        // no-op
    }

    public static void removeLife() {
        life -= 1;
        if (life <= 0) {
            endGame();
        }
    }

    public static void endGame() {

    }

    public static void draw(Graphics2D g) {
        int padding = 8;
        g.drawRect(rect.x + padding, rect.y + padding, rect.width, rect.height);
        g.setFont(font);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g.drawString(displayText, rect.x + (rect.width / 6), rect.y + 50);
        g.drawString(Integer.toString(life), rect.x + (rect.width / 6), rect.y + 100);
    }

    static {
        try {
            File fontFile = new File("assets/fonts/AtariFont.ttf");
            font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            font = font.deriveFont(30f);
        } catch (IOException | FontFormatException e) {
            System.out.println(e.getLocalizedMessage());
            font = new Font("Arial", Font.PLAIN, 30);
        }

    }

}
