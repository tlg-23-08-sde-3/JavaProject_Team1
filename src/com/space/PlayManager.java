package com.space;

import com.space.ui.HealthUI;
import com.space.ui.ScoreUI;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PlayManager {

    Ship ship = new Ship();
    List<Asteroid> asteroids = Stream.generate(Asteroid::new)
            .limit(50)
            .collect(Collectors.toList());

    public void update() {
        ship.update();
        asteroids.forEach(Asteroid::update);
        checkAsteroidCollisions();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.setStroke(new BasicStroke(2f));
        ship.draw(g);
        ship.drawBullets(g);
        asteroids.forEach(asteroid -> asteroid.draw(g));
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


}


