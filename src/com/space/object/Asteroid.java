package com.space.object;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

public class Asteroid extends SpaceObject {

    // static fields
    private static final double MAX_VELOCITY_LARGE = 0.4;
    private static final double MAX_VELOCITY_MEDIUM = 0.8;
    private static final double MAX_VELOCITY_SMALL = 1.2;

    // fields
    private AsteroidSize asteroidSize;

    // constructors
    public Asteroid() {
        super();
        setRandomSize();
        setRandomOutsideLocation();
        setVelocityTowardsCenter();
        setRandomShape();
    }

    public Asteroid(AsteroidSize asteroidSize, double locationX, double locationY, double velocityX, double velocityY) {
        super();
        this.asteroidSize = asteroidSize;
        this.locationX = locationX;
        this.locationY = locationY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        setRandomShape();
    }

    // action methods
    private void setRandomVelocityBySize() {
        double velocityRange = asteroidVelocityBySize();
        velocityX = (Math.random() * 2 * velocityRange) - velocityRange;
        velocityY = (Math.random() * 2 * velocityRange) - velocityRange;
    }

    public double asteroidVelocityBySize() {
        double velocityRange = MAX_VELOCITY_LARGE;
        switch (asteroidSize) {
            case MEDIUM:
                velocityRange = MAX_VELOCITY_MEDIUM;
                break;
            case SMALL:
                velocityRange = MAX_VELOCITY_SMALL;
                break;
        }
        return velocityRange;
    }

    public void limitAsteroidVelocityBySize() {
        double limit = asteroidVelocityBySize();
        double currentSpeed = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        if (currentSpeed > limit) {
            velocityX *= limit / currentSpeed;
            velocityY *= limit / currentSpeed;
        }
    }

    private void setRandomSize() {
        int randomSize = (int) (Math.random() * 3);
        switch (randomSize) {
            case 0:
                asteroidSize = AsteroidSize.SMALL;
                break;
            case 1:
                asteroidSize = AsteroidSize.MEDIUM;
                break;
            case 2:
                asteroidSize = AsteroidSize.LARGE;
                break;
        }
    }

    private void setRandomShape() {
        shape = new Path2D.Double();
        boolean firstPoint = true;
        for (int i = 0; i < 10; i++) {
            int angle = 36 * i;
            double distance = asteroidSize.getLowerLimit() + (int) (Math.random() * (asteroidSize.getUpperLimit() - asteroidSize.getLowerLimit()));
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
        move();
        limitAsteroidVelocityBySize();
        checkBounds();
    }

    public List<Asteroid> split() {
        List<Asteroid> splitAsteroids = new ArrayList<>();
        double[] centroid = getCentroid();
        switch (asteroidSize) {
            case LARGE:
                for (int i = 0; i < 2; i++) {
                    Asteroid asteroid = new Asteroid(AsteroidSize.MEDIUM, centroid[0], centroid[1], velocityX, velocityY);
                    splitAsteroids.add(asteroid);
                }
                break;
            case MEDIUM:
                for (int i = 0; i < 2; i++) {
                    Asteroid asteroid = new Asteroid(AsteroidSize.SMALL, centroid[0], centroid[1], velocityX, velocityY);
                    splitAsteroids.add(asteroid);
                }
                break;
        }
        isActive = false;
        return splitAsteroids;
    }

    public void setRandomOutsideLocation() {
        int frameSide = (int) (Math.random() * 4);
        double buffer = AsteroidSize.LARGE.getUpperLimit();
        double extendedBuffer = buffer * 2;
        switch (frameSide) {
            case 0: // top
                locationX = Math.random() * MAX_LOCATION_X;
                locationY = MIN_LOCATION_Y - buffer - (Math.random() * (extendedBuffer - buffer));
                break;
            case 1: // bottom
                locationX = Math.random() * MAX_LOCATION_X;
                locationY = MAX_LOCATION_Y + buffer + (Math.random() * (extendedBuffer - buffer));
                break;
            case 2: // left
                locationX = MIN_LOCATION_X - buffer - (Math.random() * (extendedBuffer - buffer));
                locationY = Math.random() * MAX_LOCATION_Y;
                break;
            case 3: // right
                locationX = MAX_LOCATION_X + buffer + (Math.random() * (extendedBuffer - buffer));
                locationY = Math.random() * MAX_LOCATION_Y;
                break;
        }
    }

    private void setVelocityTowardsCenter() {
        double centerX = MAX_LOCATION_X / 2.0;
        double centerY = MAX_LOCATION_Y / 2.0;
        double offsetRange = 100;
        double offsetX = (Math.random() * 2 * offsetRange) - offsetRange;
        double offsetY = (Math.random() * 2 * offsetRange) - offsetRange;
        double targetX = centerX + offsetX;
        double targetY = centerY + offsetY;
        double dx = targetX - locationX;
        double dy = targetY - locationY;
        double magnitude = Math.sqrt(dx * dx + dy * dy);
        double directionX = dx / magnitude;
        double directionY = dy / magnitude;
        setRandomVelocityBySize();
        velocityX = directionX * Math.abs(velocityX);
        velocityY = directionY * Math.abs(velocityY);
    }

    // getters and setters
    public AsteroidSize getAsteroidSize() {
        return asteroidSize;
    }
}
