package com.space;

import com.space.ui.HealthUI;
import com.space.ui.ScoreUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PlayManager {

    Ship ship = new Ship();
    List<Asteroid> asteroids = Stream.generate(Asteroid::new)
            .limit(1)
            .collect(Collectors.toList());

    public void update() {
        ship.update();
        asteroids.forEach(Asteroid::update);
        checkAsteroidCollisions();
        checkBulletAsteroidCollisions();
        checkShipAsteroidCollisions();
        cleanupObjects();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.setStroke(new BasicStroke(2f));
        ship.draw(g);
        //ship.bullets.stream().filter(Bullet::isActive).forEach(bullet -> bullet.draw(g));
        //asteroids.stream().filter(Asteroid::isActive).forEach(asteroid -> asteroid.draw(g));
        ship.bullets.forEach(bullet -> bullet.draw(g));
        asteroids.forEach(asteroid -> asteroid.draw(g));
        ScoreUI.draw(g);
        HealthUI.draw(g);
    }

    public void checkShipAsteroidCollisions() {
        for (Asteroid asteroid : asteroids) {
            if (ship.intersectsWith(asteroid)) {
                // updateLives();
                ship.defaultShip();
                break;
            }
        }
    }

    public void checkAsteroidCollisions() {
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid asteroid1 = asteroids.get(i);
            for (int j = i + 1; j < asteroids.size(); j++) {
                Asteroid asteroid2 = asteroids.get(j);
                if (asteroid1.intersectsWith(asteroid2)) {
                    asteroid1.bounce(asteroid2);
                }
            }
        }
    }


    public void checkBulletAsteroidCollisions() {
        List<Asteroid> newAsteroids = new ArrayList<>();
        for (Bullet bullet : ship.bullets) {
            for (Asteroid asteroid : asteroids) {
                if (bullet.intersectsWith(asteroid)) {
                    // updateScore();
                    bullet.isActive = false;
                    bullet.audio.closeAudioSystem();
                    newAsteroids.addAll(asteroid.split());
                }
            }
        }
        asteroids.addAll(newAsteroids);
    }

    public void cleanupObjects() {
        int initialBulletCount = ship.bullets.size();
        ship.bullets.removeIf(bullet -> !bullet.isActive);
        int finalBulletCount = ship.bullets.size();
        System.out.println("Bullets before cleanup: " + initialBulletCount + ", after cleanup: " + finalBulletCount);

        int initialAsteroidCount = asteroids.size();
        asteroids.removeIf(asteroid -> !asteroid.isActive);
        int finalAsteroidCount = asteroids.size();
        System.out.println("Asteroids before cleanup: " + initialAsteroidCount + ", after cleanup: " + finalAsteroidCount);
    }
}


