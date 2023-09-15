package com.space;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
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
    protected Shape shape;
    protected Polygon shapePoly;

    // constructors
    private SpaceObject() {
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

        AffineTransform transform = new AffineTransform();
        transform.translate(getVelocityX(), getVelocityY());
        shapePoly.translate(getVelocityX(), getVelocityY());
        shape = transform.createTransformedShape(shape);
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
        velocityX += Math.sin(Math.toRadians(orientation)) * delta;
        velocityY -= Math.cos(Math.toRadians(orientation)) * delta;
        move();
    }


    public void decelerate(int delta) {
        velocityX -= Math.sin(Math.toRadians(orientation)) * delta;
        velocityY += Math.cos(Math.toRadians(orientation)) * delta;
        move();
    }

    public boolean intersectsWith(SpaceObject other) {
        return this.getShape().getBounds().intersects(other.getShape().getBounds());
    }

    public void destroy() {
        setActive(false);
    }

    public void draw(Graphics g) {
        g.drawPolygon(shapePoly);
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
        int[] xPoints = shapePoly.xpoints;
        int[] yPoints = shapePoly.ypoints;

        // calculate the centroid of the polygon
        double centroidX = Arrays.stream(xPoints).average().orElse(0.0);
        double centroidY = Arrays.stream(yPoints).average().orElse(0.0);

        AffineTransform transform = new AffineTransform();

        // transform shape
        transform.rotate(Math.toRadians(angle), centroidX, centroidY);
        shape = transform.createTransformedShape(shape);
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
        if (orientation < 0) orientation = 360;
        if (orientation > 360) orientation = 0;
        this.orientation = orientation;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Polygon shape) {
        this.shape = shape;
        this.shapePoly = shape;
    }

}
