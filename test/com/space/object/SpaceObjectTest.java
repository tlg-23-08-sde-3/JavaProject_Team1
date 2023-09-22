package com.space.object;

import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Path2D;

import static org.junit.Assert.*;

public class SpaceObjectTest {

    // fixtures
    SpaceObject object;

    @Before
    public void setUp() {
        object = new SpaceObject();
    }

    @Test
    public void moveTo_shouldChangeLocationOfSpaceObject() {
        object.moveTo(120, 200);
        assertEquals(120.0, object.locationX, .001);
        assertEquals(200.0, object.locationY, .001);
    }

    @Test
    public void rotateBy_shouldChangeOrientationByAngleAmount() {
        object.rotateBy(30);
        assertEquals(30, object.orientation, .001);
        object.rotateBy(-10);
        assertEquals(20, object.orientation, .001);
    }

    @Test
    public void rotateBy_shouldWrapAround360_whenAttemptingToGoNegative() {
        object.rotateBy(-10);
        assertEquals(350, object.orientation, .001);
    }

    @Test
    public void rotateBy_shouldWrapAround0_whenAttemptingToGoOver360() {
        object.rotateBy(370);
        assertEquals(10, object.orientation, .001);
    }

    @Test
    public void intersectsWith_ShouldReturnTrue_whenTwoObjectsAreOverlapping() {
        Path2D.Double shipShape = new Path2D.Double();
        shipShape.moveTo(605, 350);
        shipShape.lineTo(615, 325);
        shipShape.lineTo(625, 350);
        shipShape.lineTo(615, 340);
        shipShape.closePath();

        Path2D.Double shipShape2 = new Path2D.Double();
        shipShape2.moveTo(605, 350);
        shipShape2.lineTo(615, 325);
        shipShape2.lineTo(625, 350);
        shipShape2.lineTo(615, 340);
        shipShape2.closePath();

        SpaceObject ship = new SpaceObject(0, 0, 0, 0, shipShape);
        SpaceObject ship2 = new SpaceObject(0, 0, 0, 0, shipShape2);

        assertTrue(ship.intersectsWith(ship2));
    }
}