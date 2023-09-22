package com.space.logic;

import com.space.audio.Audio;
import com.space.object.Asteroid;
import com.space.object.Bullet;
import com.space.object.Ship;
import com.space.ui.HealthUI;
import com.space.ui.ScoreUI;

import static com.space.ui.saveload.LoadSettings.*;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main logic for managing play of the application
 */
class PlayManager {

    // fields
    private double difficultyScalingFactor = 1.0;
    private int asteroidSpawnCounter = 0;
    private final Ship ship = new Ship();
    private final List<Asteroid> asteroids = Stream.generate(Asteroid::new)
            .limit(10)
            .collect(Collectors.toList());
    private final List<Asteroid> asteroidsQueue = new ArrayList<>();

    // action methods
    public void update() {
        synchronized (this) {
            ship.update();
            Iterator<Asteroid> asteroidIterator = asteroids.iterator();
            while (asteroidIterator.hasNext()) {
                Asteroid asteroid = asteroidIterator.next();
                asteroid.update();
            }
            checkAsteroidCollisions();
            checkBulletAsteroidCollisions();
            checkShipAsteroidCollisions();
            cleanupObjects();
            asteroidSpawnCounter();
        }
    }

    private void asteroidSpawnCounter() {
        asteroidSpawnCounter++;
        if (asteroidSpawnCounter >= ASTEROID_SPAWN_INTERVAL) {
            asteroidSpawn();
            asteroidSpawnCounter = 0;
            increaseDifficulty();
        }
    }

    public void increaseDifficulty() {
        difficultyScalingFactor += DIFFICULTY_INCREMENT;
    }

    public void asteroidSpawn() {
        int numberOfAsteroidsToSpawn = (int) (1 * difficultyScalingFactor);
        int totalAsteroidsAfterSpawn = asteroids.size() + asteroidsQueue.size() + numberOfAsteroidsToSpawn;
        if (totalAsteroidsAfterSpawn > MAX_ASTEROIDS) {
            numberOfAsteroidsToSpawn = MAX_ASTEROIDS - (asteroids.size() + asteroidsQueue.size());
        }
        for (int i = 0; i < numberOfAsteroidsToSpawn; i++) {
            Asteroid asteroid = new Asteroid();
            asteroidsQueue.add(asteroid);
        }
    }

    public void draw(Graphics2D g) {
        synchronized (this) {
            g.setColor(Color.white);
            g.setStroke(new BasicStroke(2f));
            ship.draw(g);
            Iterator<Bullet> bulletIterator = ship.getBullets().iterator();
            while (bulletIterator.hasNext()) {
                Bullet bullet = bulletIterator.next();
                bullet.draw(g);
            }
            Iterator<Asteroid> asteroidDrawIterator = asteroids.iterator();
            while (asteroidDrawIterator.hasNext()) {
                Asteroid asteroid = asteroidDrawIterator.next();
                asteroid.draw(g);
            }
            ScoreUI.draw(g);
            HealthUI.draw(g);
        }
    }

    private void checkShipAsteroidCollisions() {
        if (!ship.isInvulnerable()) {
            Iterator<Asteroid> asteroidIterator = asteroids.iterator();
            while (asteroidIterator.hasNext()) {
                Asteroid asteroid = asteroidIterator.next();
                if (ship.intersectsWith(asteroid)) {
                    HealthUI.removeLife();
                    ship.defaultShip();
                    Audio.playSound(1);
                    break;
                }
            }
        }
    }

    private void checkAsteroidCollisions() {
        Iterator<Asteroid> asteroidIterator1 = asteroids.iterator();
        while (asteroidIterator1.hasNext()) {
            Asteroid asteroid1 = asteroidIterator1.next();
            Iterator<Asteroid> asteroidIterator2 = asteroids.listIterator(asteroids.indexOf(asteroid1) + 1);
            while (asteroidIterator2.hasNext()) {
                Asteroid asteroid2 = asteroidIterator2.next();
                if (asteroid1.intersectsWith(asteroid2)) {
                    asteroid1.bounce(asteroid2);
                }
            }
        }
    }

    private void checkBulletAsteroidCollisions() {
        Iterator<Bullet> bulletIterator = ship.getBullets().iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Iterator<Asteroid> asteroidIterator = asteroids.iterator();
            while (asteroidIterator.hasNext()) {
                Asteroid asteroid = asteroidIterator.next();
                if (bullet.intersectsWith(asteroid)) {
                    ScoreUI.addScore(10);
                    bullet.setActive(false);
                    asteroidsQueue.addAll(asteroid.split());
                    Audio.playSound(1);
                }
            }
        }
    }

    private void cleanupObjects() {
        ship.getBullets().removeIf(bullet -> !bullet.isActive());
        asteroids.removeIf(asteroid -> !asteroid.isActive());
        asteroids.addAll(asteroidsQueue);
        asteroidsQueue.clear();
    }

    // getters and setters
    public int getAsteroidSpawnCounter() {
        return asteroidSpawnCounter;
    }

    public double getDifficultyScalingFactor() {
        return difficultyScalingFactor;
    }

    public List<Asteroid> getAsteroidsQueue() {
        return asteroidsQueue;
    }
}
