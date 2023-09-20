package com.space.frames;

import com.space.GameFrame;
import com.space.GamePanel;
import com.space.KeyHandler;
import com.space.ui.HealthUI;
import com.space.ui.ScoreUI;


/**
 * Resets static variables from the game based on defaults given on creation
 */
public class ResetGame {
    private static final int healthDefault = HealthUI.life;
    private static final int scoreDefault = ScoreUI.score;

    public static void reset() {
        // reset score and health
        HealthUI.life = healthDefault;
        ScoreUI.score = scoreDefault;

        // reset keys and bindings
        KeyHandler.resetKeys();

        // create a new game frame, and make it run
        GameFrame game = new GameFrame();
        GamePanel.isRunning = true;
        game.startGame();
    }

    public static void touch() {
        // grabs defaults
    }

}
