package com.space;

enum AsteroidSize {
    SMALL(20.0, 30.0),
    MEDIUM(40.0, 50.0),
    LARGE(60.0, 70.0);

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

