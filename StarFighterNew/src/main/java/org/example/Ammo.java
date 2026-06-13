package org.example;

import java.awt.*;

public abstract class Ammo {

    protected int x;
    protected int y;
    protected final int width;
    protected final int height;
    protected final int speed;
    protected final int damage;
    protected final Color color;
    protected boolean active = true;

    protected Ammo(int x, int y, int width, int height, int speed, int damage, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.damage = damage;
        this.color = color;
    }

    /** Move the bullet upward and deactivate it once it leaves the screen. */
    public void update() {
        y -= speed;
        if (y + height < 0) {
            active = false;
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    public int getDamage() {
        return damage;
    }
}
