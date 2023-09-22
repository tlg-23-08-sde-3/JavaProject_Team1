package com.space.ui.saveload;

import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.*;

public class FontLoaderTest {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void fontLoader_loadsAtariFont_whenFontFound() {
        Font font = FontLoader.loadFont("assets/fonts/AtariFont.ttf", 22f);
        assertEquals("Press Start 2P", font.getFontName());
        assertEquals(22f, font.getSize(), .001);
    }

    @Test
    public void fontLoader_loadsDefaultFont_whenFontNotFound() {
        Font font = FontLoader.loadFont("random/directory/that/does/not/exist.wav", 2);
        assertEquals("Arial", font.getFontName());
        assertEquals(22f, font.getSize(), .001);
    }
}