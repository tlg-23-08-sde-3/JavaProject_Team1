package com.space.object;

import com.space.logic.GamePanel;
import com.space.logic.KeyHandler;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShipTest {

    // fixture
    Ship ship;

    @Before
    public void setUp() {
        ship = new Ship();
    }

    // defaultShip() test
    @Test
    public void shouldReturnEquals_whenShipIsAtCorrectLocation() {
        ship.defaultShip();
        assertEquals((double) GamePanel.GAME_WIDTH / 2, ship.getLocationX(), .001);
        assertEquals((double) GamePanel.GAME_HEIGHT / 2, ship.getLocationY(), .001);
    }

    // handleMovement() test
    @Test
    public void shouldReturnTrue_whenUpPressedKey() {
        KeyHandler.upPressed = true;
        ship.handleMovement();
        assertTrue(ship.getVelocityX() != 0 || ship.getVelocityY() != 0);
    }
}