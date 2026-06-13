package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class Alien {
    private static final Random RANDOM = new Random();
    private static final String IMAGE_PATH = "/player/alien.jpg";
    private static BufferedImage sprite;

    static {
        try (InputStream in = Alien.class.getResourceAsStream(IMAGE_PATH)) {
            if (in != null) {
                sprite = ImageIO.read(in);
            } else {
                System.err.println("Could not find " + IMAGE_PATH + " on classpath.");
            }
        } catch (IOException e) {
            System.err.println("Failed to load alien sprite: " + e.getMessage());
        }
    }

    private int x;
    private int y;
    private final int width = GameConstants.ALIEN_WIDTH;
    private final int height = GameConstants.ALIEN_HEIGHT;
    private int health;
    private int speed;

    public Alien(int x, int y) {
        this.x = x;
        this.y = y;
        this.health = GameConstants.ALIEN_START_HEALTH;
        this.speed = randomSpeed();
    }

    /** Moves the alien down; respawns it at the top if it exits the screen. */
    public void update() {
        y += speed;
        if (y > GameConstants.HEIGHT) {
            respawnAtTop();
        }
    }

    /** Resets this alien to full health at a new random spot above the screen. */
    public void respawnAtTop() {
        x = RANDOM.nextInt(GameConstants.WIDTH - width);
        y = -height;
        health = GameConstants.ALIEN_START_HEALTH;
        speed = randomSpeed();
    }

    private int randomSpeed() {
        return GameConstants.ALIEN_BASE_SPEED + RANDOM.nextInt(2);
    }

    public void hit(int damage) {
        health -= damage;
        if (health <= 0) {
            respawnAtTop();
        }
    }

    public boolean isDestroyed() {
        return health <= 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics2D g2d) {
        if (sprite != null) {
            g2d.drawImage(sprite, x, y, width, height, null);
        } else {
            // fallback so the game still runs if the image is missing
            g2d.setColor(java.awt.Color.RED);
            g2d.fillOval(x, y, width, height);
        }
    }
}
