package com.space;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

class SpaceObject extends JPanel {

    // fields
    protected boolean isActive;
    protected int locationX, locationY;
    protected int velocityX, velocityY;
    protected double orientation;
    protected Polygon shapePoly;
    protected Shape shape;

    // constructors
    public SpaceObject() {
        this.isActive = true;
        shapePoly = new Polygon();
        shape = shapePoly;
    }

    public SpaceObject(int locationX, int locationY, int velocityX, int velocityY, Polygon shape) {
        this();
        this.locationX = locationX;
        this.locationY = locationY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.shapePoly = shape;
        this.shape = shape;
    }

    // action methods
    public void move() {
        locationX += velocityX;
        locationY += velocityY;
        wrapAroundFrame();
        shapePoly.translate(velocityX, velocityY);
        AffineTransform transform = new AffineTransform();
        transform.translate(velocityX, velocityY);
        shape = transform.createTransformedShape(shape);
    }

    private void wrapAroundFrame() {
        if (locationX > PlayManager.WIDTH) {
            locationX = 0;
        }
        if (locationX < 0) {
            locationX = PlayManager.WIDTH;
        }
        if (locationY > GamePanel.GAME_HEIGHT) {
            locationY = 0;
        }
        if (locationY < 0) {
            locationY = GamePanel.GAME_HEIGHT;
        }
    }

    public void accelerate(int delta) {
        velocityX += (Math.sin(Math.toRadians(orientation)) * delta);
        velocityY -= (Math.cos(Math.toRadians(orientation)) * delta);
        move();
    }


    public void decelerate(int delta) {
        velocityX -= (Math.sin(Math.toRadians(orientation)) * delta);
        velocityY += (Math.cos(Math.toRadians(orientation)) * delta);
        move();
    }

    // rotate by a certain amount
    public void rotateBy(double angle) {
        setOrientation(orientation + angle);
        rotateShape(angle);
    }

    // rotate to a specific value
    public void rotateTo(double angle) {
        double difference = angle - orientation;
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


    public boolean intersectsWith(SpaceObject other) {
        return this.shapePoly.getBounds().intersects(other.shapePoly.getBounds());
    }

    public void destroy() {
        isActive = false;
    }

    public void draw(Graphics graphics) {
        graphics.drawPolygon(shapePoly);
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
        this.orientation = orientation % 360;
        if (this.orientation < 0) this.orientation += 360;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Polygon shape) {
        this.shape = shape;
        this.shapePoly = shape;
    }

}
