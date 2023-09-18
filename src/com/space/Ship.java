package com.space;

import java.awt.*;

class Ship extends SpaceObject {

    private final int MAX_SPEED = 3;
    private final double rotationSpeed = 5.0;
    private double angle = 0;

    public Ship() {
        super();
        defaultShip();
    }

    public void defaultShip() {
        // default shape of the ship
        int[] xPoints = {605, 615, 625, 615};
        int[] yPoints = {350, 325, 350, 340};
        int numberOfPoints = 4;

        locationX = PlayManager.WIDTH / 2;
        locationY = GamePanel.GAME_HEIGHT / 2;
        velocityX = 0;
        velocityY = 0;
        shapePoly = new Polygon(xPoints, yPoints, numberOfPoints);
        shape = shapePoly;
    }

    private void handleMovement() {
        if (KeyHandler.upPressed) {
            // keep speed under max
            if (getSqrMagnitude() < MAX_SPEED) {
                accelerate(4);
            }
        }
        if (KeyHandler.upReleased) {
            handleAccel();
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

    private void handleAccel() {
        if (getSqrMagnitude() > 0) {
            decelerate(1);
        }
    }

    private void rotate() {
        rotateBy(angle);
    }

    public void update() {
        handleMovement();
        handleAccel();
        rotate();
        System.out.println("locationX: " + locationX
                + " | locationY: " + locationY);
        System.out.println("BoundsX: " + shapePoly.getBounds().x
                + " | BoundsY: " + shapePoly.getBounds().y
                + " | SqrMag: " + getSqrMagnitude());
    }

    // get the square magnitude of the ship's velocity
    private double getSqrMagnitude() {
        return Math.sqrt(Math.pow(velocityX, 2) + Math.pow(velocityY, 2));
    }

    public void draw(Graphics2D graphics) {
        graphics.draw(shape);
    }

}
