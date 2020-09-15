package com.fighter.star.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class SpaceShip extends MovingObject {

    private int speed;
    private Image image;

    public SpaceShip() {
        this(0, 0, 0);
    }

    public SpaceShip(int x, int y) {
        this(x, y, 0);
    }

    public SpaceShip(int x, int y, int s) {
        super(x, y);
        speed = s;

        try {
            URL resource = getClass().getResource("/ship2.png");
            System.out.println("imageName: "+ resource.getFile());
            image = ImageIO.read(resource);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void setX(int x) {
        super.setX(x);
    }

    @Override
    public void setY(int y) {
        super.setY(y);
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
        g.drawImage(image, getX(), getY(), 80, 80, null);
    }

    @Override
    public String toString() {
        return "Pos " + super.getX() + " " + super.getY() + " Speed " + getSpeed();
    }
}
