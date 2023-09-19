package com.space;

import java.awt.*;
import java.awt.geom.Rectangle2D;
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
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.setStroke(new BasicStroke(2f));
        ship.draw(g);
        ship.drawBullets(g);
        asteroids.forEach(asteroid -> asteroid.draw(g));
    }

}
