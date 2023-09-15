package com.space;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.Arrays;

class Ship extends JPanel {
    int x = 600;
    int y = 300;
    int[] xPoints = {620, 640, 660, 640};
    int[] yPoints = {355, 300, 355, 340};

    final int MAX_SPEED = 3;

    int numberOfPoints = 4;
    int speed = 1;
    double rotationSpeed = 5.0;
    double angle = 0;
    Polygon shipPoly;

    SpaceObject ship;
    public Ship() {
        shipPoly = new Polygon(xPoints, yPoints, numberOfPoints);
        ship = new SpaceObject(x, y, 0, 0, shipPoly);
    }

    private void handleMovement() {
        if (KeyHandler.upPressed) {
            // keep speed under max
            if (getSqrMagnitude() < MAX_SPEED){
                ship.accelerate(4);
            }
            KeyHandler.upPressed = false;
        }
        if (KeyHandler.upReleased) {
            KeyHandler.upReleased = false;
        }
        // rotate counterclockwise
        if (KeyHandler.leftPressed) {
            angle = rotationSpeed*-1;
            KeyHandler.leftPressed = false;
        }
        // rotate clockwise
        if (KeyHandler.rightPressed) {
            angle = rotationSpeed;
            KeyHandler.rightPressed = false;
        }
        // reset turning
        if (KeyHandler.rightReleased || KeyHandler.leftReleased) {
            angle = 0;
            KeyHandler.rightReleased = false;
            KeyHandler.leftReleased = false;
        }

        if (KeyHandler.shootPressed) {
            // instantiate projectile
        }
    }

    private void handleAccel() {
        if (getSqrMagnitude() > 0) {
            ship.decelerate(1);
        }
    }


    private void rotate() {
        ship.rotateBy(angle);
    }

    public void update() {
        handleMovement();
        handleAccel();
        rotate();
        System.out.println("X: " + ship.shape.getBounds().x
                + " | Y: " + ship.shape.getBounds().y
                + " | SqrMag: " + getSqrMagnitude());
    }

    // get the square magnitude of the ship's velocity
    private double getSqrMagnitude(){
        return Math.sqrt(Math.pow(ship.velocityX,2) + Math.pow(ship.velocityY,2));
    }

    public void draw(Graphics2D graphics) {
        graphics.draw(ship.shape);
    }


}
