package com.space;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

class Asteroid extends SpaceObject {

    private AsteroidSize size;

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
                size = AsteroidSize.SMALL;
                break;
            case 1:
                size = AsteroidSize.MEDIUM;
                break;
            case 2:
                size = AsteroidSize.LARGE;
                break;
        }
    }


    public void setRandomLocation() {
        double offSetLocation = AsteroidSize.LARGE.getUpperLimit();
        double minLocationX = MIN_LOCATION_X + offSetLocation;
        double maxLocationX = MAX_LOCATION_X - offSetLocation;
        double minLocationY = MIN_LOCATION_Y + offSetLocation;
        double maxLocationY = MAX_LOCATION_Y - offSetLocation;
        locationX = minLocationX + (Math.random() * (maxLocationX - minLocationX));
        locationY = minLocationY + (Math.random() * (maxLocationY - minLocationY));
    }

    public void setRandomShape() {
        shape = new Path2D.Double();
        boolean firstPoint = true;
        for (int i = 0; i < 6; i++) {
            int angle = 60 * i;
            double distance = size.getLowerLimit() + (int) (Math.random() * (size.getUpperLimit() - size.getLowerLimit()));
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
