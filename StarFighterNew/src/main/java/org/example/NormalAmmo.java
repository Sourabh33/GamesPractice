package org.example;

import java.awt.*;

public class NormalAmmo extends Ammo {

    private static final int BULLET_WIDTH = 4;
    private static final int BULLET_HEIGHT = 10;
    private static final int SPEED = 6;
    private static final int DAMAGE = 1;

    public NormalAmmo(int x, int y) {
        super(x, y, BULLET_WIDTH, BULLET_HEIGHT, SPEED, DAMAGE, Color.YELLOW);
    }
}
