package com.space;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

class Ship extends JPanel {
    int x = 600;
    int y = 300;
    int[] xPoints = {620, 640, 660, 640};
    int[] yPoints = {355, 300, 355, 340};

    int numberOfPoints = 4;
    int speed = 1;
    double rotationSpeed = 1.0;
    double angle = 0;
    Shape shipPoly;

    public Ship() {
        shipPoly = new Polygon(xPoints, yPoints, numberOfPoints);
    }

    private void handleMovement() {
        if (KeyHandler.upPressed) {
            // move towards top of ship

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

    private void rotation() {
        Rectangle bounds = shipPoly.getBounds();
        AffineTransform affineTransform =
                AffineTransform.getRotateInstance(Math.toRadians(angle), x + (bounds.width / 2), y + (bounds.height / 2));
        shipPoly = affineTransform.createTransformedShape(shipPoly);
    }

    public void update() {
        handleMovement();
        rotation();
    }

    public void draw(Graphics2D graphics) {
        //super.paint(graphics);
        //graphics.rotate(Math.toRadians(angle));
        graphics.draw(shipPoly);
    }

//    public void paintComponent(Graphics g) {
//        super.paintComponent(g);
//        Graphics2D g2d = (Graphics2D)g;
//        g2d.setColor(Color.WHITE);
//        g2d.draw(shipPoly);
//    }


}
