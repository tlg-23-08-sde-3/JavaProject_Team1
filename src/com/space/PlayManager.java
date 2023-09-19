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
            .limit(20)
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
                    separateAsteroids(asteroid1, asteroid2);
                    asteroid1.bounce();
                    asteroid2.bounce();
                }
            }
        }
    }

    public void separateAsteroids(Asteroid asteroid1, Asteroid asteroid2) {
        // Calculate the distance between the centers of the two asteroids
        double dx = asteroid2.locationX - asteroid1.locationX;
        double dy = asteroid2.locationY - asteroid1.locationY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        // Calculate the total radius (sum of the radii of the two asteroids)
        double totalRadius = asteroid1.size.getUpperLimit() + asteroid2.size.getUpperLimit();

        // Calculate the overlap distance
        double overlap = totalRadius - distance;

        // If there's no overlap, no need to separate
        if (overlap <= 0) return;

        // Normalize the direction vector
        dx /= distance;
        dy /= distance;

        // Determine which asteroid to move
        Asteroid asteroidToMove;
        if (asteroid1.size.ordinal() < asteroid2.size.ordinal()) {
            asteroidToMove = asteroid1;
        } else {
            asteroidToMove = asteroid2;
        }

        // Calculate new positions for the asteroid based on the overlap
        double newLocationX = asteroidToMove.locationX + dx * overlap;
        double newLocationY = asteroidToMove.locationY + dy * overlap;

        // Move the asteroid to the new position
        asteroidToMove.moveTo(newLocationX, newLocationY);
    }


}


