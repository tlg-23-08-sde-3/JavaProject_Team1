package com.space;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;

class Ship extends SpaceObject {

    private final double MAX_SPEED = 3.0;
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
            shoot();
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
        updateBullets();
    }

    private void updateBullets() {
        bullets.forEach(Bullet::move);
        bullets.removeIf(bullet -> !bullet.isActive);
    }

    // get the square magnitude of the ship's velocity
    private double getSqrMagnitude() {
        return Math.sqrt(Math.pow(velocityX, 2) + Math.pow(velocityY, 2));
    }

    private void shoot() {
        double centroidX = shape.getBounds().getCenterX();
        double centroidY = shape.getBounds().getCenterY();
        Bullet bullet = new Bullet(centroidX, centroidY, orientation);
        bullets.add(bullet);
        System.out.println(bullets.size());
    }

    public void drawBullets(Graphics2D graphics) {
        bullets.forEach(bullet -> bullet.draw(graphics));
    }
}
