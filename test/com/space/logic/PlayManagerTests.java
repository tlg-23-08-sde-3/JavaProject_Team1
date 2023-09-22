package com.space.logic;

import static com.space.ui.saveload.LoadSettings.*;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

public class PlayManagerTests {

    // fixtures
    PlayManager playManager;

    @Before
    public void setUp() {
        playManager = new PlayManager();
    }

    // asteroidSpawnCounter() tests
    @Test
    public void shouldReturnEquals_whenUpdateIncreasesAsteroidSpawnCounter() {
        int initialCounter = playManager.getAsteroidSpawnCounter();
        playManager.update();
        int updatedCounter = playManager.getAsteroidSpawnCounter();
        assertEquals(initialCounter + 1, updatedCounter);
    }

    @Test
    public void shouldReturnEquals_whenAsteroidSpawnCounterResetsAfterInterval() {
        for (int i = 0; i < ASTEROID_SPAWN_INTERVAL; i++) {
            playManager.update();
        }
        assertEquals(0, playManager.getAsteroidSpawnCounter());
    }

    // increaseDifficulty() test
    @Test
    public void shouldReturnEquals_whenIncreaseDifficultyUpdatesScalingFactor() {
        double initialFactor = playManager.getDifficultyScalingFactor();
        playManager.increaseDifficulty();
        double updatedFactor = playManager.getDifficultyScalingFactor();
        assertEquals(initialFactor + DIFFICULTY_INCREMENT, updatedFactor, .001);
    }

    // asteroidSpawn() test
    @Test
    public void shouldReturnTrue_whenAsteroidSpawnLimitsAsteroidCount() {
        playManager.asteroidSpawn();
        int totalAsteroids = playManager.getAsteroidsQueue().size();
        assertTrue(totalAsteroids <= MAX_ASTEROIDS);
    }
}
