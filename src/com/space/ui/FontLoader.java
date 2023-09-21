package com.space.ui;

import java.awt.*;
import java.io.File;

public class FontLoader {

    // constructors
    private FontLoader() {
        // no-op, static class
    }

    public static Font loadFont(String fontString, float fontSize) {
        Font font;
        try {
            File file = new File(fontString);
            font = Font.createFont(Font.TRUETYPE_FONT, file);
            font = font.deriveFont(fontSize);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
            font = new Font("Arial", Font.PLAIN, 22);
        }
        return font;
    }
}
