package com.space.object;

public enum AsteroidSize {
    SMALL(10.0, 30.0),
    MEDIUM(20.0, 40.0),
    LARGE(30.0, 50.0);

    private final double lowerLimit;
    private final double upperLimit;

    AsteroidSize(double lowerLimit, double upperLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public double getLowerLimit() {
        return lowerLimit;
    }

    public double getUpperLimit() {
        return upperLimit;
    }
}

