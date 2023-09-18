package com.space;

import java.awt.*;

class Asteroid extends SpaceObject {

    private int[] sizeLimits;

    public Asteroid() {
        super();
        defaultAsteroid();
    }

    public void defaultAsteroid() {
        setRandomSize();
        setRandomLocation();
        setRandomShape();
        velocityX = 0;
        velocityY = 0;
        shape = shapePoly;
    }

    public void setRandomSize() {
        int randomSize = (int) (Math.random() * 3);
        switch (randomSize) {
            case 0:
                sizeLimits = new int[]{10, 30};       // small size, arbitrary value for now
                break;
            case 1:
                sizeLimits = new int[]{30, 50};       // medium size, arbitrary value for now
                break;
            case 2:
                sizeLimits = new int[]{50, 70};       // large size, arbitrary value for now
                break;
        }
    }


    public void setRandomLocation() {
        int minLocationX = ((GamePanel.GAME_WIDTH / 2) - (PlayManager.WIDTH / 2) + 70); // edge of the rectangle + the largest size asteroid
        int maxLocationX = PlayManager.WIDTH + 70;
        int minLocationY = 70;
        int maxLocationY = GamePanel.GAME_HEIGHT - 70;

        locationX = minLocationX + (int) (Math.random() * ((maxLocationX - minLocationX) + 1));
        locationY = minLocationY + (int) (Math.random() * ((maxLocationY - minLocationY) + 1));
        System.out.println("locationX: " + locationX + " | locationY: " + locationY);
    }

    public void setRandomShape() {
        for (int i = 0; i < 6; i++) {
            int angle = 60 * i;
            int distance = sizeLimits[0] + (int) (Math.random() * ((sizeLimits[1] - sizeLimits[0]) + 1));
            shapePoly.addPoint(locationX + (int) (distance * Math.cos(Math.toRadians(angle))),
                    locationY + (int) (distance * Math.sin(Math.toRadians(angle))));
        }
    }

    public void update() {
        for (int i = 0; i < shapePoly.npoints; i++) {
            shapePoly.xpoints[i] += velocityX;
            shapePoly.ypoints[i] += velocityY;
        }
    }

    public void draw(Graphics2D graphics) {
        graphics.drawPolygon(shapePoly);
    }

}
