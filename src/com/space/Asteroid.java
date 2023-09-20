package com.space;

import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

class Asteroid extends SpaceObject {

    public AsteroidSize size;

    public Asteroid() {
        super();
        setRandomAsteroid();
    }

    public Asteroid(AsteroidSize size, double locationX, double locationY, double velocityX, double velocityY) {
        super();
        this.size = size;
        this.locationX = locationX;
        this.locationY = locationY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        setRandomShape();
    }

    public void setRandomAsteroid() {
        setRandomSize();
        setRandomLocation();
        setRandomShape();
        setRandomVelocity();
    }

    public void setRandomVelocity() {
        double velocityRange = 0.3;
        switch (size) {
            case MEDIUM:
                velocityRange = 0.6;
                break;
            case SMALL:
                velocityRange = 0.9;
                break;
        }
        velocityX = (Math.random() * 2 * velocityRange) - velocityRange;
        velocityY = (Math.random() * 2 * velocityRange) - velocityRange;
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
        for (int i = 0; i < 10; i++) {
            int angle = 36 * i;
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
        limitVelocity(1);
    }

    public List<Asteroid> split() {
        List<Asteroid> splitAsteroids = new ArrayList<>();
        switch (size) {
            case LARGE:
                for (int i = 0; i < 2; i++) {
                    Asteroid asteroid = new Asteroid(AsteroidSize.MEDIUM, locationX, locationY, velocityX, velocityY);
                    splitAsteroids.add(asteroid);
                }
                break;
            case MEDIUM:
                for (int i = 0; i < 2; i++) {
                    Asteroid asteroid = new Asteroid(AsteroidSize.SMALL, locationX, locationY, velocityX, velocityY);
                    splitAsteroids.add(asteroid);
                }
                break;
        }
        isActive = false;
        return splitAsteroids;
    }

    public boolean isLarge() {
        return this.size == AsteroidSize.LARGE;
    }

    public boolean isMedium() {
        return this.size == AsteroidSize.MEDIUM;
    }

}
