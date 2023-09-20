package com.space;

import com.space.ui.ScoreUI;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main Player Ship class
 */
class Ship extends SpaceObject {

    private final double MAX_SPEED = 1.0;
    private final double MIN_SPEED = 0.005;
    private final double accelerationRate = 0.025;
    private final double decelerationRate = 0.01;
    private final double rotationSpeed = 5.0;
    private double angle = 0;
    public List<Bullet> bullets = new ArrayList<>();

    public Ship() {
        super();
        defaultShip();
    }

    /**
     * Handler to generate a default ship at a default location
     */
    public void defaultShip() {
        // default shape of the ship
        Path2D.Double shipShape = new Path2D.Double();
        shipShape.moveTo(605, 350);
        shipShape.lineTo(615, 325);
        shipShape.lineTo(625, 350);
        shipShape.lineTo(615, 340);
        shipShape.closePath();

        locationX = (double) GamePanel.GAME_WIDTH / 2;
        locationY = (double) GamePanel.GAME_HEIGHT / 2;
        velocityX = 0;
        velocityY = 0;
        shape = shipShape;
    }

    /**
     * Utilizes KeyListener and handles object movement based on keys pressed/released
     */
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
            //System.out.println("pew pew");
            shoot();
        }
    }

    /**
     * Allows the ship to wrap around the screen
     * If the ship goes off the left side, it will reappear on the right side, same
     */
    private void wrapAroundScreen() {
        double[] centroid = getCentroid();
        Point2D centroidPoint = new Point2D.Double(centroid[0], centroid[1]);
        if (centroidPoint.getX() < 0) {
            moveTo(GamePanel.GAME_WIDTH, 0);
        }
        if (centroidPoint.getX() > GamePanel.GAME_WIDTH) {
            moveTo(-GamePanel.GAME_WIDTH, 0);
        }
        if (centroidPoint.getY() < 0) {
            moveTo(0, GamePanel.GAME_HEIGHT);
        }
        if (centroidPoint.getY() > GamePanel.GAME_HEIGHT) {
            moveTo(0, -GamePanel.GAME_HEIGHT);
        }
    }

    /**
     * Decelerates if the current magnitude is above the max speed.
     * Cleans up some jittery movement when swapping directions.
     */
    private void limitSpeed() {
        if (getSqrMagnitude() > MAX_SPEED) {
            decelerate(decelerationRate, MAX_SPEED, MIN_SPEED);
        }
    }

    /**
     * General execution method for updating functions every frame
     */
    public void update() {
        handleMovement();
        limitSpeed();
        rotateBy(angle);
        wrapAroundScreen();
        updateBullets();
    }

    /**
     * Move each bullet and remove them if they are off of the screen
     * Controls delay counter to delay how often bullets are instantiated
     */
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
                        "Angle:    (d)%s \n" +
                        "-----------------------------\n\n",
                shape.getBounds().x, shape.getBounds().y, velocityX,
                velocityY, getSqrMagnitude(), locationX, locationY, orientation);
    }

    /**
     * Get positive magnitude (add squared values of velocities then square root)
     *
     * @return double
     */
    private double getSqrMagnitude() {
        return Math.sqrt(Math.pow(velocityX, 2) + Math.pow(velocityY, 2));
    }

    /**
     * Creates a new bullet if a certain amount of frames has passed.
     * Adds the bullet to a list for cleanup later.
     */
    private void shoot() {
        if (Bullet.bulletDelayCounter >= Bullet.BULLET_DELAY) {
            double centroidX = shape.getBounds().getCenterX();
            double centroidY = shape.getBounds().getCenterY();
            Bullet bullet = new Bullet(centroidX, centroidY, orientation);
            bullets.add(bullet);
            Bullet.bulletDelayCounter = 0;
            //System.out.println("Bullets: " + bullets.size());
        }
    }

    /**
     * Draw each bullet and pass along to their own draw function for handling movement
     */
    public void drawBullets(Graphics2D graphics) {
        bullets.forEach(bullet -> bullet.draw(graphics));
    }
}
