package com.fighter.star.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;

public class Alien extends MovingObject {

    private int speed;
    private Image image;

    public Alien() {
        this(0, 0, 0);
    }

    public Alien(int x, int y) {
        this(x, y, 0);
    }

    public Alien(int x, int y, int s) {
        super(x, y);
        speed = s;
        try {
            image = ImageIO.read(new File("E:\\Java Programs\\GamesPractice\\StarFighter\\src\\main\\resources\\alien.jpg"));
        } catch (Exception e) {

        }
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
        return "Alien " + super.toString() + " Speed " + getSpeed();
    }
}
