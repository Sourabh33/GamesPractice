package com.fighter.star.game;

import java.awt.*;

public abstract class MovingObject implements Locatable {
    private int xPos;
    private int yPos;

    public MovingObject() {
        xPos = 0;
        yPos = 0;
    }

    public MovingObject(int x, int y) {
        setPos(x, y);
    }

    @Override
    public void setPos(int x, int y) {
        xPos = x;
        yPos = y;
    }

    @Override
    public void setX(int x) {
        xPos = x;
    }

    @Override
    public void setY(int y) {
        yPos = y;
    }

    @Override
    public int getX() {
        return xPos;
    }

    @Override
    public int getY() {
        return yPos;
    }

    public abstract void setSpeed(int s);
    public abstract int getSpeed();

    public abstract void draw(Graphics g);

    public void move(String direction) {
        if(direction.equals(GameConstant.DIR_LEFT)) {
            setX(getX() - getSpeed());
        } else if(direction.equals(GameConstant.DIR_RIGHT)) {
            setX(getX() + getSpeed());
        } else if(direction.equals(GameConstant.DIR_UP)) {
            setY(getY() + getSpeed());
        } else if(direction.equals(GameConstant.DIR_DOWN)) {
            setY(getY() - getSpeed());
        }
    }
}
