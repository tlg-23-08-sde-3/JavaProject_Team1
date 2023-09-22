package com.space.ui;

import static org.junit.Assert.*;

import org.junit.Test;

public class HealthUITests {

    @Test
    public void removeLife_shouldReturnMinusOne_whenCalledEachTime() {
        HealthUI.removeLife();
        assertEquals(2, HealthUI.life);
        HealthUI.removeLife();
        assertEquals(1, HealthUI.life);
        HealthUI.removeLife();
        assertEquals(0, HealthUI.life);
    }
}
