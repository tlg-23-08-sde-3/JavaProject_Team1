package com.space;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

class Asteroid extends SpaceObject {

    private double[] sizeLimits;

    public Asteroid() {
        super();
        defaultAsteroid();
    }

    public void defaultAsteroid() {
        setRandomSize();
        setRandomLocation();
        setRandomShape();
        velocityX = 0.0;
        velocityY = 0.0;
    }

    public void setRandomSize() {
        int randomSize = (int) (Math.random() * 3);
        switch (randomSize) {
            case 0:
                sizeLimits = new double[]{10.0, 30.0};       // small size, arbitrary value for now
                break;
            case 1:
                sizeLimits = new double[]{30.0, 50.0};       // medium size, arbitrary value for now
                break;
            case 2:
                sizeLimits = new double[]{50.0, 70.0};       // large size, arbitrary value for now
                break;
        }
    }


    public void setRandomLocation() {
        double offsetLocation = 70;
        double minLocationX = MIN_LOCATION_X; // edge of the rectangle + the largest size asteroid
        double maxLocationX = MAX_LOCATION_X;
        double minLocationY = offsetLocation;
        double maxLocationY = GamePanel.GAME_HEIGHT - 70;

        locationX = minLocationX + (Math.random() * ((maxLocationX - minLocationX) + 1));
        locationY = minLocationY + (Math.random() * ((maxLocationY - minLocationY) + 1));
    }

    public void setRandomShape() {
        shape = new Path2D.Double();
        boolean firstPoint = true;
        for (int i = 0; i < 6; i++) {
            int angle = 60 * i;
            double distance = sizeLimits[0] + (int) (Math.random() * ((sizeLimits[1] - sizeLimits[0]) + 1));
            double x = locationX + (distance * Math.cos(Math.toRadians(angle)));
            double y = locationY + (distance * Math.sin(Math.toRadians(angle)));
            if (firstPoint) {
                shape.moveTo(x, y);
                firstPoint = false;
            } else {
                shape.lineTo(x, y);
            }
        }
        shape.closePath();
    }

    public void update() {
        AffineTransform transform = new AffineTransform();
        transform.translate(velocityX, velocityY);
        shape.transform(transform);
    }
}
