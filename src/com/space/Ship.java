package com.space;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

class Ship extends SpaceObject {

    private final double MAX_SPEED = 1.0;
    private final double MIN_SPEED = 0.005;
    private final double accelerationRate = 0.025;
    private final double decelerationRate = 0.01;
    private final double rotationSpeed = 5.0;
    private double angle = 0;
    private List<Bullet> bullets = new ArrayList<>();

    public Ship() {
        super();
        defaultShip();
    }

    public void defaultShip() {
        // default shape of the ship
        Path2D.Double shipShape = new Path2D.Double();
        shipShape.moveTo(605, 350);
        shipShape.lineTo(615, 325);
        shipShape.lineTo(625, 350);
        shipShape.lineTo(615, 340);
        shipShape.closePath();

        locationX = (double) PlayManager.WIDTH / 2;
        locationY = (double) GamePanel.GAME_HEIGHT / 2;
        velocityX = 0;
        velocityY = 0;
        shape = shipShape;
    }

    private void handleMovement() {
        if (KeyHandler.upPressed) {
            accelerate(accelerationRate, MAX_SPEED, MIN_SPEED);
        }
        if (KeyHandler.upReleased) {
            decelerate(decelerationRate, MAX_SPEED, MIN_SPEED);
        }
        // rotate counterclockwise
        if (KeyHandler.leftPressed) {
            angle = rotationSpeed * -1;
        }
        // rotate clockwise
        if (KeyHandler.rightPressed) {
            angle = rotationSpeed;
        }
        // reset turning
        if (KeyHandler.leftReleased) {
            if (!KeyHandler.rightPressed) {
                angle = 0;
            }
            KeyHandler.leftReleased = false;
        }

        if (KeyHandler.rightReleased) {
            if (!KeyHandler.leftPressed) {
                angle = 0;
            }
            KeyHandler.rightReleased = false;
        }

        if (KeyHandler.shootPressed) {
            System.out.println("pew pew");
            shoot();
        }
    }

    private void limitSpeed() {
        if (getSqrMagnitude() > MAX_SPEED) {
            System.out.println("limitspeed CALLEDDD");
            decelerate(decelerationRate, MAX_SPEED, MIN_SPEED);
        }
    }

    private void rotate() {
        rotateBy(angle);
    }

    public void update() {
        handleMovement();
        limitSpeed();
        rotate();
        updateBullets();
    }

    private void updateBullets() {
        bullets.forEach(Bullet::move);
        for (int i = 0; i < bullets.size(); i++) {
            if (!bullets.get(i).isActive) {
                bullets.get(i).audio.closeAudioSystem();
                bullets.remove(i);
            }
        }
        Bullet.bulletDelayCounter++;
        if (Bullet.bulletDelayCounter > Bullet.BULLET_DELAY) {
            Bullet.bulletDelayCounter = Bullet.BULLET_DELAY;
        }
        //bullets.removeIf(bullet -> !bullet.isActive);
        //System.out.println(this);
    }

    @Override
    public String toString() {
        return String.format("---------------------------\n" +
                        "Bounds:   (x)%s (y)%s \n" +
                        "Velocity: (x)%s (y)%s (sqrM)%s \n" +
                        "Location: (x)%s (y)%s \n" +
                        "Angle:    (d)%s \n"+
                            "-----------------------------\n\n",
                shape.getBounds().x, shape.getBounds().y, velocityX,
                velocityY, getSqrMagnitude(), locationX, locationY, orientation);
    }

    // get the square magnitude of the ship's velocity
    private double getSqrMagnitude() {
        return Math.sqrt(Math.pow(velocityX, 2) + Math.pow(velocityY, 2));
    }

    private void shoot() {
        if (Bullet.bulletDelayCounter >= Bullet.BULLET_DELAY) {
            double centroidX = shape.getBounds().getCenterX();
            double centroidY = shape.getBounds().getCenterY();
            Bullet bullet = new Bullet(centroidX, centroidY, orientation);
            bullets.add(bullet);
            Bullet.bulletDelayCounter = 0;
            System.out.println(bullets.size());
        }
    }

    public void drawBullets(Graphics2D graphics) {
        bullets.forEach(bullet -> bullet.draw(graphics));
    }
}
