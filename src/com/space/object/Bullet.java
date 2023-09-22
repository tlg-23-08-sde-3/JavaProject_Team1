package com.space.object;

import com.space.audio.Audio;

import java.awt.geom.Path2D;

/**
 * Main projectile of the application
 * Instantiated with a velocity
 * Disappears upon leaving the play window or hitting an asteroid
 */
public class Bullet extends SpaceObject {

    // static fields
    public static final int BULLET_DELAY = 20;
    public static int bulletDelayCounter = BULLET_DELAY;

    // fields
    private final double bulletSpeed = 20;

    // constructors
    public Bullet(double locationX, double locationY, double orientation) {
        super(locationX, locationY, 0, 0, new Path2D.Double());
        this.orientation = orientation;
        velocityX = bulletSpeed * Math.sin(Math.toRadians(orientation));
        velocityY = -bulletSpeed * Math.cos(Math.toRadians(orientation));
        createBulletShape();
        Audio.playSound(0);
    }

    // action methods
    private void createBulletShape() {
        Path2D.Double bulletShape = new Path2D.Double();
        double size = 1;
        bulletShape.moveTo(locationX - size, locationY - size);
        bulletShape.lineTo(locationX + size, locationY - size);
        bulletShape.lineTo(locationX + size, locationY + size);
        bulletShape.lineTo(locationX - size, locationY + size);
        bulletShape.closePath();
        shape = bulletShape;
    }
}
