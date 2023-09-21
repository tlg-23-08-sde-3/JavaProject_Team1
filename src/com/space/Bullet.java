package com.space;

import com.space.audio.Audio;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;

class Bullet extends SpaceObject {

    public static final int BULLET_DELAY = 10;
    public static int bulletDelayCounter = BULLET_DELAY;

    private final double bulletSpeed = 20;

    private final String bulletAudioString = "assets/audio/laserShoot.wav";
    public Audio audio;

    public Bullet(double locationX, double locationY, double orientation) {
        super(locationX, locationY, 0, 0, new Path2D.Double());
        this.orientation = orientation;
        velocityX = bulletSpeed * Math.sin(Math.toRadians(orientation));
        velocityY = -bulletSpeed * Math.cos(Math.toRadians(orientation));
        createBulletShape();
        Audio.playSound(0);
    }

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
