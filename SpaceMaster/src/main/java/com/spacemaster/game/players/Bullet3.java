package com.spacemaster.game.players;

import com.spacemaster.game.Game;
import com.spacemaster.game.pojo.GameObject;
import com.spacemaster.game.utils.Texture;
import com.spacemaster.game.pojo.BasePlayer;

import java.awt.*;

public class Bullet3 extends GameObject implements BasePlayer {

    private Texture texture;
    private int level;

    public Bullet3(double x, double y, Texture texture, Game game) {
        super(x, y);
        this.texture = texture;
        level = game.getLevel();
    }

    public Bullet3(double x, double y) {
        super(x, y);
    }

    @Override
    public void tick() {
        if (level >= 1 && level <= 3) {
            y -= 6;
            x -= 1.5;
        } else if (level > 3 && level <= 6) {
            y -= 9;
            x -= 1.5;
        } else {
            y -= 12;
            x -= 1.5;
        }
    }

    @Override
    public void render(Graphics g) {
        if (level >= 1 && level <= 3) {
            g.drawImage(texture.missile[0], (int) x, (int) y, null);
        } else if (level > 3 && level <= 5) {
            g.drawImage(texture.missile[1], (int) x, (int) y, null);
        } else if (level > 5 && level <= 7) {
            g.drawImage(texture.missile[2], (int) x, (int) y, null);
        } else if (level > 7 && level <= 9) {
            g.drawImage(texture.missile[3], (int) x, (int) y, null);
        } else {
            g.drawImage(texture.missile[4], (int) x, (int) y, null);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }
}
