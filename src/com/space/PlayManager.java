package com.space;

import java.awt.*;

class PlayManager {
    private static int left_x;
    private static int right_x;
    private static int top_y;
    private static int bottom_y;

    private static final int WIDTH = 960;

    Ship ship = new Ship();
    Asteroid asteroid = new Asteroid();

    public PlayManager() {

        left_x = (GamePanel.GAME_WIDTH / 2) - (WIDTH / 2);
        right_x = left_x + WIDTH;
        top_y = 0;
        bottom_y = GamePanel.GAME_HEIGHT;

    }

    public void update(){
        ship.update();
        asteroid.update();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.white);
        g.setStroke(new BasicStroke(4f));
        g.drawRect(left_x-4, top_y-4, WIDTH+8, bottom_y+8);
        ship.draw(g);
        asteroid.draw(g);
    }

}
