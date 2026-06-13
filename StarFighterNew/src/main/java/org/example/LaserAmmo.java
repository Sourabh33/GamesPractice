package org.example;

import java.awt.*;

/**
 * Heavier ammo: bigger, faster, deals more damage per hit.
 */
public class LaserAmmo extends Ammo {

    private static final int BULLET_WIDTH = 6;
    private static final int BULLET_HEIGHT = 20;
    private static final int SPEED = 10;
    private static final int DAMAGE = 3;

    public LaserAmmo(int x, int y) {
        super(x, y, BULLET_WIDTH, BULLET_HEIGHT, SPEED, DAMAGE, Color.CYAN);
    }
}
