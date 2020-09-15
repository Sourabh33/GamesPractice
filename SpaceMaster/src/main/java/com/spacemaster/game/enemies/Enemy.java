package com.spacemaster.game.enemies;

import com.spacemaster.game.*;
import com.spacemaster.game.players.Player;
import com.spacemaster.game.pojo.BasePlayer;
import com.spacemaster.game.pojo.BaseEnemy;
import com.spacemaster.game.pojo.GameObject;
import com.spacemaster.game.utils.*;

import java.awt.*;
import java.util.Random;

public class Enemy extends GameObject implements BaseEnemy {

    private final Random random = new Random();
    private Texture texture;
    private Game game;
    private Controller controller;
    private int level;
    private final int speed = random.nextInt(3) + 4 / 3;
    private Player player;
    private Sound sound;

    public Enemy(double x, double y) {
        super(x, y);
    }

    public Enemy(double x, double y, Texture texture, Controller controller, Game game) {
        super(x, y);
        this.texture = texture;
        this.controller = controller;
        this.game = game;
        player = game.getPlayer();
        level = game.getLevel();
        this.sound = game.getSound();
    }

    @Override
    public void tick() {
        y += speed + (game.getLevel() >> 1);
        setX(player.getX());

        //in case enemy goes out of frame, again getting it back to the top
        if (y > Game.HEIGHT * GameHelper.SCALE) {
            setExplicitX(random.nextInt(640));
            y = -5;
        }

        for (BasePlayer playerGroup : game.getPlayerEntity()) {
            if (Physics.Collision(this, playerGroup)) {
                sound.playExplosion();
                controller.removeEntity(playerGroup);
                controller.removeEntity(this);
                game.updateScore(100 * level);
                game.setEnemy_killed(game.getEnemy_killed() + 1);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (level >= 1 && level <= 3) {
            g.drawImage(texture.enemy[5], (int) x, (int) y, null);
        } else if (level > 3 && level <= 6) {
            g.drawImage(texture.enemy[1], (int) x, (int) y, null);
        } else if (level > 6 && level <= 9) {
            g.drawImage(texture.enemy[2], (int) x, (int) y, null);
        } else if (level > 9 && level <= 11) {
            g.drawImage(texture.enemy[3], (int) x, (int) y, null);
        } else if (level > 11 && level <= 15) {
            g.drawImage(texture.enemy[4], (int) x, (int) y, null);
        } else {
            g.drawImage(texture.enemy[5], (int) x, (int) y, null);
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

    public void setY(double y) {
        this.y = y * 0.8;
    }

    public void setX(double x) {
        if (x < this.x) {
            this.x -= 0.5;
        } else {
            this.x += 0.5;
        }
    }

    public void setExplicitX(double x) {
        this.x = x;
    }
}
