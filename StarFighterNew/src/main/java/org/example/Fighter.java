package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Fighter {
    private static final String IMAGE_PATH = "/player/ship.jpg";
    private static BufferedImage sprite;

    static {
        try (InputStream in = Fighter.class.getResourceAsStream(IMAGE_PATH)) {
            if (in != null) {
                sprite = ImageIO.read(in);
            } else {
                System.err.println("Could not find " + IMAGE_PATH + " on classpath.");
            }
        } catch (IOException e) {
            System.err.println("Failed to load ship sprite: " + e.getMessage());
        }
    }

    private int x;
    private int y;
    private final int width = GameConstants.FIGHTER_WIDTH;
    private final int height = GameConstants.FIGHTER_HEIGHT;

    private int health = GameConstants.FIGHTER_START_HEALTH;
    private AmmoType currentAmmoType = AmmoType.NORMAL;

    public Fighter(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveLeft() {
        x = Math.max(0, x - GameConstants.FIGHTER_SPEED);
    }

    public void moveRight() {
        x = Math.min(GameConstants.WIDTH - width, x + GameConstants.FIGHTER_SPEED);
    }

    public void switchAmmo(AmmoType type) {
        this.currentAmmoType = type;
    }

    /** Creates a new projectile of the currently selected ammo type. */
    public Ammo fire() {
        int bulletStartX = x + width / 2;
        if (currentAmmoType == AmmoType.LASER) {
            return new LaserAmmo(bulletStartX - 3, y);
        }
        return new NormalAmmo(bulletStartX - 2, y);
    }

    public void takeDamage(int amount) {
        health = Math.max(0, health - amount);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getHealth() {
        return health;
    }

    public AmmoType getCurrentAmmoType() {
        return currentAmmoType;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void draw(Graphics2D g2d) {
        if (sprite != null) {
            g2d.drawImage(sprite, x, y, width, height, null);
        } else {
            // fallback so the game still runs if the image is missing
            g2d.setColor(java.awt.Color.GREEN);
            g2d.fillRect(x, y, width, height);

            int[] xPoints = {x, x + width / 2, x + width};
            int[] yPoints = {y, y - 15, y};
            g2d.fillPolygon(xPoints, yPoints, 3);
        }
    }
}
