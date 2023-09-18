package com.space;

import java.awt.geom.Path2D;

class Ship extends SpaceObject {

    private final double MAX_SPEED = 1.0;
    private final double MIN_SPEED = 0.005;
    private final double accelerationRate = 0.025;
    private final double decelerationRate = 0.01;
    private final double rotationSpeed = 5.0;
    private double angle = 0;

    public Ship() {
        super();
        defaultShip();
    }

    public void defaultShip() {
        // default shape of the ship
        Path2D.Double shipPath = new Path2D.Double();
        shipPath.moveTo(605, 350);
        shipPath.lineTo(615, 325);
        shipPath.lineTo(625, 350);
        shipPath.lineTo(615, 340);
        shipPath.closePath();

        locationX = PlayManager.WIDTH / 2;
        locationY = GamePanel.GAME_HEIGHT / 2;
        velocityX = 0;
        velocityY = 0;
        shape = shipPath;
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
        System.out.println(this);
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
}
