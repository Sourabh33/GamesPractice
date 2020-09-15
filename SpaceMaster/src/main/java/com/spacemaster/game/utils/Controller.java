package com.spacemaster.game.utils;

import com.spacemaster.game.Game;
import com.spacemaster.game.enemies.Asteroid;
import com.spacemaster.game.enemies.Enemy;
import com.spacemaster.game.pojo.BasePlayer;
import com.spacemaster.game.pojo.BaseEnemy;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class Controller {

    private List<BasePlayer> bullets = new CopyOnWriteArrayList<>();
    private List<BaseEnemy> enemies = new CopyOnWriteArrayList<>();

    BasePlayer bullet;
    BaseEnemy enemy;

    private final Random random = new Random();
    private final Texture texture;
    private final Game game;

    public Controller(Texture texture, Game game) {
        this.texture = texture;
        this.game = game;
    }

    public void createEnemy(int enemy_count) {
        for (int i = 0; i < enemy_count / 2; i++) {
            addEntity(new Enemy(random.nextInt(640), -10, texture, this, game));
            addEntity(new Asteroid(random.nextInt(640), -10, texture, this, game));
        }
    }

    public void tick() {
        bullets.forEach(b -> {
            b.tick();
//            bullet = b;
//            bullet.tick();
        });

        enemies.forEach(e -> {
            e.tick();
//            enemy = e;
//            enemy.tick();
        });
    }

    public void render(Graphics g) {
        bullets.forEach(b -> {
            b.render(g);
//            bullet = b;
//            bullet.render(g);
        });

        enemies.forEach(e -> {
            e.render(g);
//            enemy = e;
//            enemy.render(g);
        });
    }

    public void addEntity(BasePlayer block) {
        bullets.add(block);
    }

    public void addEntity(BaseEnemy block) {
        enemies.add(block);
    }

    public void removeEntity(BasePlayer block) {
        bullets.remove(block);
    }

    public void removeEntity(BaseEnemy block) {
        enemies.remove(block);
    }

    public List<BasePlayer> getBullets() {
        return bullets;
    }

    public List<BaseEnemy> getEnemies() {
        return enemies;
    }
}
