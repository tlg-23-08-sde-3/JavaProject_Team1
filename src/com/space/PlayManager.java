package com.space;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PlayManager {

    Ship ship = new Ship();
    List<Asteroid> asteroids = Stream.generate(Asteroid::new)
            .limit(2)
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
    }

    public void checkAsteroidCollisions() {
        for (int i = 0; i < asteroids.size(); i++) {
            Asteroid asteroid1 = asteroids.get(i);
            for (int j = i + 1; j < asteroids.size(); j++) {
                Asteroid asteroid2 = asteroids.get(j);
                if (asteroid1.intersectsWith(asteroid2)) {
                    asteroid1.bounce();
                    asteroid2.bounce();
                }
            }
        }
    }


}
