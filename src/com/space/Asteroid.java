package com.space;

import javax.swing.*;
import java.awt.*;

class Asteroid extends JPanel {
    int xVelocity = 0;
    int yVelocity = 0;
    Polygon asteroidPoly;

    public Asteroid() {
        asteroidPoly = setRandomShape(1,100);
        xVelocity = setVelocity(xVelocity);
        yVelocity = setVelocity(yVelocity);
        System.out.println("X: " + xVelocity + "\nY: " + yVelocity);
    }

    public void update() {
        for (int i=0; i < asteroidPoly.npoints; i++) {
            asteroidPoly.xpoints[i] += xVelocity;
            asteroidPoly.ypoints[i] += yVelocity;
        }
    }

    private int setVelocity(int num) {
        //return (int)Math.floor(Math.random() * (2 - (-2) + 1) + (-2));
        return 0;
    }

    public Polygon setRandomShape(int minDistance, int maxDistance) {
        Polygon shape = new Polygon();
        for (int i = 0; i < 6; i++) {
            int angle = 60 * i;
            int distance = minDistance + (int) (Math.random() * ((maxDistance - minDistance) + 1));
            int locationX = 300;
            int locationY = 100;
            shape.addPoint(locationX + (int) (distance * Math.cos(Math.toRadians(angle))),
                    locationY + (int) (distance * Math.sin(Math.toRadians(angle))));
        }
        return shape;
    }

    public void draw(Graphics2D graphics) {
        //super.paint(graphics);
        graphics.drawPolygon(asteroidPoly);
    }

}
