package com.spacemaster.game.players;

import com.spacemaster.game.*;
import com.spacemaster.game.pojo.BasePlayer;
import com.spacemaster.game.pojo.BaseEnemy;
import com.spacemaster.game.pojo.GameObject;
import com.spacemaster.game.utils.Controller;
import com.spacemaster.game.utils.Physics;
import com.spacemaster.game.utils.Sound;
import com.spacemaster.game.utils.Texture;

import java.awt.*;
import java.util.Random;

public class Player extends GameObject implements BasePlayer {
    private double velX = 0;
    private double velY = 0;

    private Texture texture;
    private Game game;
    private Controller controller;
    private int randomPlayer;
    private Sound sound;

    public Player(double x, double y) {
        super(x, y);
    }

    public Player(double x, double y, Texture tex, Game game, Controller controller) {
        super(x, y);
        this.texture = tex;
        this.game = game;
        this.controller = controller;
        this.randomPlayer = new Random().nextInt(5);
        this.sound = game.getSound();
    }


    @Override
    public void tick() {
        x += velX;
        y += velY;

        //Controlling player just inside the frame
        if (x <= 0)
            x = 0;
        if (x >= 640 - 32)
            x = 640 - 32;
        if (y < 0)
            y = 0;
        if (y >= 480 - 40)
            y = 480 - 40;

        for (BaseEnemy enemyGroup : game.getEnemyEntity()) {
            if (Physics.Collision(this, enemyGroup)) {
                controller.removeEntity(enemyGroup);
                Game.HEALTH -= 20;			// This will decrease heal bar. Total player life available = 200/20 = 10
                game.setEnemy_killed(game.getEnemy_killed() + 1);
                sound.playHitSound();		// Sound when player gets hit by enemy
            }
        }
    }

    @Override
    public void render(Graphics g) {
        if (randomPlayer == 4) {
            g.drawImage(texture.mainPlayer, (int) x, (int) y, null);
        } else {
            g.drawImage(texture.players[randomPlayer], (int) x, (int) y, null);
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 32, 32);
    }

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

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }
}
