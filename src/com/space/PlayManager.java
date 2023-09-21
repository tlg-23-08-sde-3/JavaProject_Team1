package com.space;

import com.space.audio.Audio;
import com.space.ui.HealthUI;
import com.space.ui.ScoreUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PlayManager {

    private int asteroidStreamCounter = 0;
    private static final int ASTEROID_STREAM_INTERVAL = 30;

    Ship ship = new Ship();
    List<Asteroid> asteroids = Stream.generate(Asteroid::new)
            .limit(10)
            .collect(Collectors.toList());
    List<Asteroid> asteroidsQueue = new ArrayList<>();

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
            asteroidStreamCounter();
        }
    }

    private void asteroidStreamCounter() {
        asteroidStreamCounter++;
        if (asteroidStreamCounter >= ASTEROID_STREAM_INTERVAL) {
            asteroidStream();
            asteroidStreamCounter = 0;
        }
    }

    private void asteroidStream() {
        Asteroid asteroid = new Asteroid();
        synchronized (this) {
            asteroidsQueue.add(asteroid);
        }
    }

    public void draw(Graphics2D g) {
        synchronized (this) {
            g.setColor(Color.white);
            g.setStroke(new BasicStroke(2f));
            ship.draw(g);
            Iterator<Bullet> bulletIterator = ship.bullets.iterator();
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

    public void checkShipAsteroidCollisions() {
        if (!ship.isInvulnerable) {
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

    public void checkAsteroidCollisions() {
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


    public void checkBulletAsteroidCollisions() {
        Iterator<Bullet> bulletIterator = ship.bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            Iterator<Asteroid> asteroidIterator = asteroids.iterator();
            while (asteroidIterator.hasNext()) {
                Asteroid asteroid = asteroidIterator.next();
                if (bullet.intersectsWith(asteroid)) {
                    ScoreUI.addScore(10);
                    bullet.isActive = false;
                    asteroidsQueue.addAll(asteroid.split());
                    Audio.playSound(1);
                }
            }
        }
    }

    public void cleanupObjects() {
        //int initialBulletCount = ship.bullets.size();
        ship.bullets.removeIf(bullet -> !bullet.isActive);
        //int finalBulletCount = ship.bullets.size();
        //System.out.println("Bullets before cleanup: " + initialBulletCount + ", after cleanup: " + finalBulletCount);
        //int initialAsteroidCount = asteroids.size();
        asteroids.removeIf(asteroid -> !asteroid.isActive);
        asteroids.addAll(asteroidsQueue);
        asteroidsQueue.clear();
        //int finalAsteroidCount = asteroids.size();
        //System.out.println("Asteroids before cleanup: " + initialAsteroidCount + ", after cleanup: " + finalAsteroidCount);
    }
}
