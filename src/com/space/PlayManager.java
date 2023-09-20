package com.space;

import com.space.ui.HealthUI;
import com.space.ui.ScoreUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
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
        cleanupObjects();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.setStroke(new BasicStroke(2f));
        ship.draw(g);
        ship.bullets.stream().filter(Bullet::isActive).forEach(bullet -> bullet.draw(g));
        asteroids.stream().filter(Asteroid::isActive).forEach(asteroid -> asteroid.draw(g));
        ScoreUI.draw(g);
        HealthUI.draw(g);
    }

    public void checkAsteroidCollisions() {
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid asteroid1 = asteroids.get(i);
            for (int j = i + 1; j < asteroids.size(); j++) {
                Asteroid asteroid2 = asteroids.get(j);
                if (asteroid1.intersectsWith(asteroid2)) {
                    bounceAsteroids(asteroid1, asteroid2);
                }
            }
        }
    }

    public void bounceAsteroids(Asteroid asteroid1, Asteroid asteroid2) {
        double[] centroid1 = asteroid1.getCentroid();
        double[] centroid2 = asteroid2.getCentroid();
        double dx = centroid2[0] - centroid1[0];
        double dy = centroid2[1] - centroid1[1];
        double distance = Math.sqrt(dx * dx + dy * dy);
        double nx = dx / distance;
        double ny = dy / distance;
        double bounceFactor = 0.1;
        asteroid1.velocityX -= nx * bounceFactor;
        asteroid1.velocityY -= ny * bounceFactor;
        asteroid2.velocityX += nx * bounceFactor;
        asteroid2.velocityY += ny * bounceFactor;
    }

    public void checkBulletAsteroidCollisions() {
        List<Asteroid> newAsteroids = new ArrayList<>();
        for (Bullet bullet : ship.bullets) {
            for (Asteroid asteroid : asteroids) {
                if (bullet.intersectsWith(asteroid)) {
                    bullet.isActive = false;
                    System.out.println("Bullet is false");
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
        //System.out.println("Bullets before cleanup: " + initialBulletCount + ", after cleanup: " + finalBulletCount);

        int initialAsteroidCount = asteroids.size();
        asteroids.removeIf(asteroid -> !asteroid.isActive);
        int finalAsteroidCount = asteroids.size();
        //System.out.println("Asteroids before cleanup: " + initialAsteroidCount + ", after cleanup: " + finalAsteroidCount);
    }


}


