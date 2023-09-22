package com.space.object;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BulletTest {

    Bullet bullet;

    @Before
    public void setUp() {
        bullet = new Bullet(0, 0 , 0);
    }

    @Test
    public void shouldReturnNotNull_whenCreatingBulletShape() {
        assertNotNull(bullet.getShape());
    }
}