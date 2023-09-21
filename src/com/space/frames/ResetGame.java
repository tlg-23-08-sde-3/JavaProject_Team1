package com.space.frames;

import com.space.logic.GamePanel;
import com.space.logic.KeyHandler;
import com.space.ui.HealthUI;
import com.space.ui.ScoreUI;


/**
 * Resets static variables from the game based on defaults given on creation
 */
public class ResetGame {

    // static fields
    private static final int healthDefault = HealthUI.life;
    private static final int scoreDefault = ScoreUI.score;

    // action methods
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
