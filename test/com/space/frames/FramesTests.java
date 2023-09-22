package com.space.frames;

import com.space.ui.HealthUI;
import com.space.ui.ScoreUI;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class FramesTests {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void reset_ResetGame_shouldReturnCorrectParametersFromUIClasses_whenInstantiated() {
        ResetGame.touch();
        ResetGame.reset();
        assertEquals(3,HealthUI.life);
        assertEquals(0,ScoreUI.score);
    }
}
