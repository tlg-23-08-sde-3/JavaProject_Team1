package com.space;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PlayManager {
    private static int left_x;
    private static int right_x;
    private static int top_y;
    private static int bottom_y;

    public static final int WIDTH = 960;

    Ship ship = new Ship();
    List<Asteroid> asteroids = Stream.generate(Asteroid::new)
            .limit(2)
            .collect(Collectors.toList());

    public PlayManager() {

        left_x = (GamePanel.GAME_WIDTH / 2) - (WIDTH / 2);
        right_x = left_x + WIDTH;
        top_y = 0;
        bottom_y = GamePanel.GAME_HEIGHT;

    }

    public void update(){
        ship.update();
        asteroids.forEach(Asteroid::update);
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.setStroke(new BasicStroke(2f));
        g.drawRect(left_x-4, top_y-4, WIDTH+8, bottom_y+8);
        ship.draw(g);
        asteroids.forEach(asteroid -> asteroid.draw(g));
    }

}
