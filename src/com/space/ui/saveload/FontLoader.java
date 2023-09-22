package com.space.ui.saveload;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontLoader {

    // constructors
    private FontLoader() {
        // no-op, static class
    }

    // action methods
    public static Font loadFont(String fontString, float fontSize) {
        Font font;
        try {
            File file = new File(fontString);
            font = Font.createFont(Font.TRUETYPE_FONT, file);
            font = font.deriveFont(fontSize);
        } catch (IOException | FontFormatException e) {
            System.out.println(e.getLocalizedMessage());
            font = new Font("Arial", Font.PLAIN, 22);
        }
        return font;
    }
}
