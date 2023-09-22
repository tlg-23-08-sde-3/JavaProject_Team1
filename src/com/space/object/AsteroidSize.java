package com.space.object;

/**
 * Enum for Asteroid class
 * Tracks the possible sizes of the three types of asteroids
 */
public enum AsteroidSize {

    // fields
    SMALL(10.0, 30.0),
    MEDIUM(20.0, 40.0),
    LARGE(30.0, 50.0);
    private final double lowerLimit;
    private final double upperLimit;

    // constructors
    AsteroidSize(double lowerLimit, double upperLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    // getters and setters
    public double getLowerLimit() {
        return lowerLimit;
    }

    public double getUpperLimit() {
        return upperLimit;
    }
}

