package com.fighter.star.game;

import java.awt.*;

public class Ammo extends MovingObject {
    private int speed;

    public Ammo() {
        this(0, 0, 0);
    }

    public Ammo(int x, int y) {
        this(x, y, 0);
    }

    public Ammo(int x, int y, int s) {
        setPos(x, y);
        setSpeed(s);
    }

    @Override
    public void setSpeed(int s) {
        speed = s;
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect(getX(), getY(), 5, 5);
    }

    @Override
    public String toString() {
        return "Ammo{" +
                "speed=" + getSpeed() +
                '}';
    }
}
