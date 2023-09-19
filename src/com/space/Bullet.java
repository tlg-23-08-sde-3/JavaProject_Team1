package com.space;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

class Bullet extends SpaceObject {

    public static final int BULLET_DELAY = 30;
    public static int bulletDelayCounter = BULLET_DELAY;

    private final double BULLET_SPEED = 5.0;

    private final String bulletAudioString = "./src/com/space/audio/laserShoot.wav";
    public Audio audio;

    public Bullet(double locationX, double locationY, double orientation) {
        super(locationX, locationY, 0, 0, new Path2D.Double());
        this.orientation = orientation;
        velocityX = BULLET_SPEED * Math.sin(Math.toRadians(orientation));
        velocityY = -BULLET_SPEED * Math.cos(Math.toRadians(orientation));
        createBulletShape();

        audio = new Audio(bulletAudioString);
        audio.playSound();
    }

    private void createBulletShape() {
        Path2D.Double bulletShape = new Path2D.Double();
        bulletShape.moveTo(0, -2); // Top point
        bulletShape.lineTo(1, 2);  // Bottom right
        bulletShape.lineTo(-1, 2); // Bottom left
        bulletShape.closePath();
        shape = bulletShape;
    }

    public void draw(Graphics2D graphics) {
        AffineTransform oldTransform = graphics.getTransform();
        graphics.translate(locationX, locationY);
        graphics.draw(shape);
        graphics.setTransform(oldTransform);
    }
}
