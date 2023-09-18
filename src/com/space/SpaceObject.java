package com.space;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

class SpaceObject extends JPanel {

    // fields
    protected boolean isActive;
    protected double locationX, locationY;
    protected double velocityX, velocityY;
    protected double orientation;
    protected Path2D shape;

    // constructors
    public SpaceObject() {
        this.isActive = true;
        shape = new Path2D.Double();
    }

    public SpaceObject(double locationX, double locationY, double velocityX, double velocityY, Path2D shape) {
        this();
        this.locationX = locationX;
        this.locationY = locationY;
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        this.shape = shape;
    }

    // action methods
    public void move() {
        locationX += velocityX;
        locationY += velocityY;

        AffineTransform transform = new AffineTransform();
        transform.translate(velocityX, velocityY);
        shape.transform(transform);
    }



    public void accelerate(double delta) {
        velocityX += (Math.sin(Math.toRadians(orientation)) * delta);
        velocityY -= (Math.cos(Math.toRadians(orientation)) * delta);
        move();
    }

    public void decelerate(double delta) {
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
        double centroidX = shape.getBounds().getCenterX();
        double centroidY = shape.getBounds().getCenterY();

        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), centroidX, centroidY);
        shape.transform(transform);
    }


    public boolean intersectsWith(SpaceObject other) {
        return this.shape.getBounds().intersects(other.shape.getBounds());
    }

    public void destroy() {
        isActive = false;
    }

    public void draw(Graphics2D graphics) {
        graphics.draw(shape);
    }

    // additional useful methods...


    // getters and setters...
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(int velocityX) {
        this.velocityX = velocityX;
    }

    public double getVelocityY() {
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

    public void setShape(Path2D shape) {
        this.shape = shape;
    }

}
