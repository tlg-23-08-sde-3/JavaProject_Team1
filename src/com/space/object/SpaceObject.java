package com.space.object;

import com.space.logic.GamePanel;

import javax.swing.JPanel;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Path2D;

/**
 * Superclass for the main game objects
 * Sets default locations, velocities, shapes and tracks activity
 */
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
        this.shape = new Path2D.Double();
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

    public void accelerate(double delta, double MAX) {
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

    public void decelerate(double delta, double MIN) {
        if (velocityX > MIN && velocityX * (-1) > MIN * (-1)) {
            velocityX -= (Math.sin(Math.toRadians(orientation)) * delta);
        }
        if (velocityY > MIN && velocityY * (-1) > MIN * (-1)) {
            velocityY += (Math.cos(Math.toRadians(orientation)) * delta);
        }
        move();
    }

    // rotate by a certain amount
    public void rotateBy(double angle) {
        setOrientation(orientation + angle);
        rotateShape(angle);
    }

    private void rotateShape(double angle) {
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

    public void checkBounds() {
        double buffer = AsteroidSize.LARGE.getUpperLimit() * 3;
        if (locationX < MIN_LOCATION_X - buffer || locationX > MAX_LOCATION_X + buffer ||
                locationY < MIN_LOCATION_Y - buffer || locationY > MAX_LOCATION_Y + buffer) {
            isActive = false;
        }
    }

    public void bounce(SpaceObject other) {
        double[] centroid1 = this.getCentroid();
        double[] centroid2 = other.getCentroid();
        double dx = centroid2[0] - centroid1[0];
        double dy = centroid2[1] - centroid1[1];
        double distance = Math.sqrt(dx * dx + dy * dy);
        double nx = dx / distance;
        double ny = dy / distance;
        double bounceFactor = 0.1;
        this.velocityX -= nx * bounceFactor;
        this.velocityY -= ny * bounceFactor;
        other.velocityX += nx * bounceFactor;
        other.velocityY += ny * bounceFactor;
    }

    public void draw(Graphics2D graphics) {
        graphics.draw(shape);
    }

    public double[] getCentroid() {
        double centroidX = shape.getBounds().getCenterX();
        double centroidY = shape.getBounds().getCenterY();
        return new double[]{centroidX, centroidY};
    }

    // getters and setters
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation % 360;
        if (this.orientation < 0) this.orientation += 360;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public double getVelocityY() {
        return velocityY;
    }

    public double getLocationX() {
        return locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public Path2D getShape() {
        return shape;
    }
}
