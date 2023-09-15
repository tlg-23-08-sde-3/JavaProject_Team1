package com.space;

import java.awt.*;
import java.util.Arrays;

public class SpaceObject {

    // static fields
    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 800;

    // fields
    protected boolean isActive;
    protected int locationX;
    protected int locationY;
    protected int velocityX;
    protected int velocityY;
    protected double orientation;
    protected Polygon shape;

    // constructors
    public SpaceObject() {
        setActive(true);
        setShape(new Polygon());
    }

    public SpaceObject(int locationX, int locationY, int velocityX, int velocityY, Polygon shape) {
        this();
        setLocationX(locationX);
        setLocationY(locationY);
        setVelocityX(velocityX);
        setVelocityY(velocityY);
        setShape(shape);
    }

    // action methods
    public void move() {
        setLocationX(getLocationX() + getVelocityX());
        setLocationY(getLocationY() + getVelocityY());
        wrapAroundFrame();
        getShape().translate(getVelocityX(), getVelocityY());
    }

    private void wrapAroundFrame() {
        if (getLocationX() > FRAME_WIDTH) {
            setLocationX(0);
        }
        if (getLocationX() < 0) {
            setLocationX(FRAME_WIDTH);
        }
        if (getLocationY() > FRAME_HEIGHT) {
            setLocationY(0);
        }
        if (getLocationY() < 0) {
            setLocationY(FRAME_HEIGHT);
        }
    }

    public void accelerate(int delta) {
        setVelocityX(getVelocityX() + (int) Math.cos(getOrientation()) * delta);
        setVelocityY(getVelocityY() + (int) Math.cos(getOrientation()) * delta);
        move();
    }

    public void decelerate(int delta) {
        setVelocityX(getVelocityX() - (int) Math.cos(getOrientation()) * delta);
        setVelocityY(getVelocityY() - (int) Math.cos(getOrientation()) * delta);
        move();
    }

    public boolean intersectsWith(SpaceObject other) {
        return this.getShape().getBounds().intersects(other.getShape().getBounds());
    }

    public void destroy() {
        setActive(false);
    }

    public void draw(Graphics g) {
        g.drawPolygon(getShape());
    }

    // rotate by a certain amount
    public void rotateBy(double angle) {
        setOrientation(getOrientation() + angle);
        rotateShape(angle);
    }

    // rotate to a specific value
    public void rotateTo(double angle) {
        double difference = angle - getOrientation();
        setOrientation(angle);
        rotateShape(difference);
    }

    private void rotateShape(double angle) {
        int[] xPoints = getShape().xpoints;
        int[] yPoints = getShape().ypoints;

        // calculate the centroid of the polygon
        double centroidX = Arrays.stream(xPoints).average().orElse(0.0);
        double centroidY = Arrays.stream(yPoints).average().orElse(0.0);

        for (int i = 0; i < getShape().npoints; i++) {
            // translate to origin to make the math simpler
            double translatedX = xPoints[i] - centroidX;
            double translatedY = yPoints[i] - centroidY;

            // rotate around origin
            double rotatedX = translatedX * Math.cos(angle) - translatedY * Math.sin(angle);
            double rotatedY = translatedX * Math.sin(angle) + translatedY * Math.cos(angle);

            // translate back to the original location
            xPoints[i] = (int) (rotatedX + centroidX);
            yPoints[i] = (int) (rotatedY + centroidY);
        }

        // update the shape with the new points
        setShape(new Polygon(xPoints, yPoints, getShape().npoints));
    }

    // additional useful methods...


    // getters and setters...
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public int getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public int getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(int velocityY) {
        this.velocityY = velocityY;
    }

    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }

    public Polygon getShape() {
        return shape;
    }

    public void setShape(Polygon shape) {
        this.shape = shape;
    }
}
