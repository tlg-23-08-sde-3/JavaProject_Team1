package com.space;

enum AsteroidSize {
    SMALL(10.0, 30.0),
    MEDIUM(30.0, 50.0),
    LARGE(50.0, 70.0);

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

