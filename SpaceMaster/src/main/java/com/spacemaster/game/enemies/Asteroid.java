package com.spacemaster.game.enemies;

import com.spacemaster.game.*;
import com.spacemaster.game.pojo.BasePlayer;
import com.spacemaster.game.pojo.BaseEnemy;
import com.spacemaster.game.pojo.GameObject;
import com.spacemaster.game.utils.*;

import java.awt.*;
import java.util.Random;

public class Asteroid extends GameObject implements BaseEnemy {

    private Random random = new Random();

    private Texture texture;
    private Game game;
    private Controller controller;
    private Sound sound;
    private final int speed = random.nextInt(3) + 1;

    public Asteroid(double x, double y) {
        super(x, y);
    }

    public Asteroid(double x, double y, Texture texture, Controller controller, Game game) {
        super(x, y);
        this.texture = texture;
        this.controller = controller;
        this.game = game;
        this.sound = game.getSound();
    }

    @Override
    public void tick() {
        y += speed + (game.getLevel() / 3.5);

        if(y > GameHelper.HEIGHT * GameHelper.SCALE) {
            x = random.nextInt(640);
            y = -3;
        }

        for (BasePlayer playerGroup: game.getPlayerEntity()) {
            if(Physics.Collision(this, playerGroup)) {
                sound.playExplosion();
                controller.removeEntity(playerGroup);
                controller.removeEntity(this);
                game.updateScore(10 * game.getLevel());
                game.setEnemy_killed(game.getEnemy_killed() + 1);
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(texture.asteroid[0], (int) x, (int) y, null);
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
        this.y = y;
    }

    public void setX(double x) {
        this.x = x;
    }
}
