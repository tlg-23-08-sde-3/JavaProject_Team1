package com.space;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;


class SpaceObject extends JPanel {

    // static variables
    public static final double MIN_LOCATION_X = 0;
    public static final double MAX_LOCATION_X = GamePanel.GAME_WIDTH;
    public static final double MIN_LOCATION_Y = 0;
    public static final double MAX_LOCATION_Y = GamePanel.GAME_HEIGHT;

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

    public void moveTo(double x, double y) {
        locationX = x;
        locationY = y;
        AffineTransform transform = new AffineTransform();
        transform.translate(x, y);
        shape.transform(transform);
    }

    public void accelerate(double delta) {
        velocityX += (Math.sin(Math.toRadians(orientation)) * delta);
        velocityY -= (Math.cos(Math.toRadians(orientation)) * delta);
        move();
    }

    public void accelerate(double delta, double MAX, double MIN) {
        // Change velocity based on angle
        velocityX += (Math.sin(Math.toRadians(orientation)) * delta);
        velocityY -= (Math.cos(Math.toRadians(orientation)) * delta);

        // check if velocity is over the max... set to max if over.
        if (MAX < velocityX) {
            velocityX = MAX;
        }
        if (-MAX > velocityX) {
            velocityX = -MAX;
        }

        if (MAX < velocityY) {
            velocityY = MAX;
        }
        if (-MAX > velocityY) {
            velocityY = -MAX;
        }
        move();
    }

    public void decelerate(double delta) {
        velocityX -= (Math.sin(Math.toRadians(orientation)) * delta);
        velocityY += (Math.cos(Math.toRadians(orientation)) * delta);
        move();
    }

    public void decelerate(double delta, double MAX, double MIN) {
        if (velocityX > MIN && velocityX * (-1) > MIN * (-1)) {
            velocityX -= (Math.sin(Math.toRadians(orientation)) * delta);
        }
        if (velocityY > MIN && velocityY * (-1) > MIN * (-1)) {
            velocityY += (Math.cos(Math.toRadians(orientation)) * delta);
        }
        move();
    }

    public void limitVelocity(double limit) {
        double currentSpeed = Math.sqrt(velocityX * velocityX + velocityY * velocityY);
        if (currentSpeed > limit) {
            velocityX *= limit / currentSpeed;
            velocityY *= limit / currentSpeed;
        }
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

    public void rotateShape(double angle) {
        double[] centroid = getCentroid();
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(angle), centroid[0], centroid[1]);
        shape.transform(transform);
    }

    public boolean intersectsWith(SpaceObject other) {
        Area thisArea = new Area(this.shape);
        Area otherArea = new Area(other.shape);
        thisArea.intersect(otherArea);
        return !thisArea.isEmpty();
    }

    public void destroy() {
        isActive = false;
    }

    public void draw(Graphics2D graphics) {
        graphics.draw(shape);
    }

    // additional useful methods...
    public double[] getCentroid() {
        double centroidX = shape.getBounds().getCenterX();
        double centroidY = shape.getBounds().getCenterY();
        return new double[]{centroidX, centroidY};
    }

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
