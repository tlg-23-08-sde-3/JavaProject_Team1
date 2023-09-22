package com.space.object;

import static com.space.object.SpaceObject.*;
import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class AsteroidTests {

    // fixtures
    Asteroid asteroid;

    @Before
    public void setUp() {
        asteroid = new Asteroid(AsteroidSize.LARGE, 0, 0, 0, 0);
    }

    // split() tests
    @Test
    public void split_shouldReturnList_whenMethodCalled() {
        List<Asteroid> asteroids = asteroid.split();
        assertEquals(2, asteroids.size());
    }

    @Test
    public void split_shouldReturnAsteroidMedium_whenLargeAsteroidSplits() {
        List<Asteroid> asteroids = asteroid.split();
        assertEquals(asteroids.get(0).getAsteroidSize(), AsteroidSize.MEDIUM);
    }

    @Test
    public void split_shouldReturnAsteroidSmall_whenMediumAsteroidSplits() {
        List<Asteroid> asteroidsMed = asteroid.split();
        List<Asteroid> asteroidsSmall = asteroidsMed.get(0).split();
        assertEquals(asteroidsSmall.get(0).getAsteroidSize(), AsteroidSize.SMALL);
    }

    // intersectsWith() test
    @Test
    public void shouldReturnTrue_whenAsteroidsAreAtSameLocation() {
        List<Asteroid> asteroids = asteroid.split();
        assertTrue(asteroids.get(0).intersectsWith(asteroids.get(1)));
    }

    // asteroidVelocityBySize() test
    @Test
    public void shouldReturnTrue_whenCorrectVelocityBySizeRange() {
        double velocityRange = asteroid.asteroidVelocityBySize();
        assertTrue(asteroid.getVelocityX() <= velocityRange && asteroid.getVelocityX() >= -velocityRange);
        assertTrue(asteroid.getVelocityY() <= velocityRange && asteroid.getVelocityY() >= -velocityRange);
    }

    // limitAsteroidVelocityBySize() test
    @Test
    public void shouldReturnTrue_whenAsteroidVelocityAtLimit() {
        asteroid.limitAsteroidVelocityBySize();
        double velocityRange = asteroid.asteroidVelocityBySize();
        double currentVelocity = Math.sqrt(asteroid.getVelocityX() * asteroid.getVelocityX() + asteroid.getVelocityY() * asteroid.getVelocityY());
        assertTrue(currentVelocity <= velocityRange);
    }

    // setRandomOutsideLocation() test
    @Test
    public void shouldReturnTrue_whenValidOutsideAsteroidLocation() {
        asteroid.setRandomOutsideLocation();
        double buffer = AsteroidSize.LARGE.getUpperLimit() * 3;
        assertTrue(asteroid.getLocationX() >= MIN_LOCATION_X - buffer && asteroid.getLocationX() <= MAX_LOCATION_X + buffer);
        assertTrue(asteroid.getLocationY() >= MIN_LOCATION_Y - buffer && asteroid.getLocationY() <= MAX_LOCATION_Y + buffer);
    }
}



