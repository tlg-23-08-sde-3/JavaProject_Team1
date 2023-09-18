package com.space;

import java.awt.geom.Path2D;

class Ship extends SpaceObject {

    private final double MAX_SPEED = 3.0;
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
            // keep speed under max
            if (getSqrMagnitude() < MAX_SPEED) {
                accelerate(4);
            }
        }
        if (KeyHandler.upReleased) {
            //handleAcceleration();
            velocityX = 0;
            velocityY = 0;
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

    private void handleAcceleration() {
        if (getSqrMagnitude() > 0) {
            decelerate(1);
        }
    }

    private void rotate() {
        rotateBy(angle);
    }

    public void update() {
        handleMovement();
        handleAcceleration();
        rotate();
        System.out.println("locationX: " + locationX
                + " | locationY: " + locationY);
        System.out.println("BoundsX: " + shape.getBounds().x
                + " | BoundsY: " + shape.getBounds().y
                + " | SqrMag: " + getSqrMagnitude());
    }

    // get the square magnitude of the ship's velocity
    private double getSqrMagnitude() {
        return Math.sqrt(Math.pow(velocityX, 2) + Math.pow(velocityY, 2));
    }
}
