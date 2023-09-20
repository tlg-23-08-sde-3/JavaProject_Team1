package com.space.ui;

import com.space.GamePanel;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;

public class HealthUI {

    public static int life = 3;
    public static String displayText = "Lives";
    private static final Rectangle rect = new Rectangle(0, 0, 180, 100);
    private static final Font font = FontLoader.loadFont("assets/fonts/AtariFont.ttf", 22f);

    private HealthUI() {
        // no-op
    }

    public static void removeLife() {
        life -= 1;
        if (life <= 0) {
            GamePanel.isRunning = false;    // ends game
        }
    }

    public static void draw(Graphics2D g) {
        int padding = 8;

        g.drawRect(rect.x + padding, rect.y + padding, rect.width, rect.height);
        g.setFont(font);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
        g.drawString(displayText, rect.x + (rect.width / 4), rect.y + 50);
        g.drawString(Integer.toString(life), rect.x + (rect.width / 4), rect.y + 90);
    }

}
