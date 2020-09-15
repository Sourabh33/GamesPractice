package com.spacemaster.game;

import com.spacemaster.game.controlinputs.KeyInput;
import com.spacemaster.game.controlinputs.MouseInput;
import com.spacemaster.game.players.Bullet1;
import com.spacemaster.game.players.Bullet2;
import com.spacemaster.game.players.Bullet3;
import com.spacemaster.game.players.Player;
import com.spacemaster.game.pojo.BasePlayer;
import com.spacemaster.game.pojo.BaseEnemy;
import com.spacemaster.game.pojo.STATE;
import com.spacemaster.game.utils.*;


import javax.sound.sampled.LineUnavailableException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static com.spacemaster.game.utils.GameHelper.*;
import static com.spacemaster.game.utils.GameHelper.SCALE;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1L;

    private static int totalContinue = 3;
    private static int level = 1;
    public static int HEALTH = 200;
    public static STATE State = STATE.MENU;
    private int score = 0;
    private int enemy_count = 8;
    private int enemy_killed = 0;

    private Thread thread;
    private final BufferedImage image = new BufferedImage(GameHelper.WIDTH, GameHelper.HEIGHT, BufferedImage.TYPE_INT_RGB);
    private BufferedImage spriteSheet = null;
    private BufferedImage background = null;
    private BufferedImage background1 = null;
    private BufferedImage mainPlayer = null;

    private boolean running = false;
    private boolean is_shooting = false;

    private Sound sound;
    private HighScoreUtil highScore;
    private Player player;
    private Controller controller;
    private Texture texture;
    private Menu menu;

    public List<BasePlayer> playerEntity;
    public List<BaseEnemy> enemyEntity;

    public Game() {
        setSize(GameHelper.WIDTH * GameHelper.SCALE, GameHelper.HEIGHT * GameHelper.SCALE);
        init();
    }

    private void init() {
        requestFocus();
        this.highScore = new HighScoreUtil();
        this.sound = new Sound();

        BufferedImageLoader loader = new BufferedImageLoader();
        this.spriteSheet = loader.loadImage(GameHelper.SHEET1);
        this.background = loader.loadImage(GameHelper.STAR_BG);
        this.background1 = loader.loadImage(GameHelper.STAR_BG1);
        this.mainPlayer = loader.loadImage(GameHelper.MAIN_PLAYER);

        addKeyListener(new KeyInput(this));
        addMouseListener(new MouseInput(sound));
        this.texture = new Texture(this);
        this.controller = new Controller(texture, this);
        this.player = new Player(GameHelper.WIDTH, GameHelper.HEIGHT, texture, this, controller);
        this.menu = new Menu();

        this.playerEntity = controller.getBullets();
        this.enemyEntity = controller.getEnemies();

        this.controller.createEnemy(enemy_count);
        if (!Sound.gameMusicPlaying) {
            try {
                this.sound.playGameMusic();
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void start() {
        if (running)
            return;
        running = true;
        this.thread = new Thread(this);
        this.thread.start();
    }

    private synchronized void stop() {
        if (!running)
            return;
        running = false;
        try {
            this.thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }


    @Override
    public void run() {
        long lastTime = System.nanoTime();
        final double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1) {
                tick();
                updates++;
                delta--;
            }

            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println(updates + " Ticks, Fps " + frames);
                updates = 0;
                frames = 0;
            }
        }
        stop();
    }

    //Created these two variable to move background image top to bottom
    //Flying illusion
    int ybg = 0;
    int ybg2 = -((GameHelper.HEIGHT * 2));

    private void tick() {
        if (ybg2 <= 0) {
            ybg2 += 1;
        } else {
            ybg2 = -((GameHelper.HEIGHT * 2));
        }

        if (ybg <= (GameHelper.HEIGHT * 2)) {
            ybg += 1;
        } else {
            ybg = 0;
        }

        if (State == STATE.GAME) {
            player.tick();
            controller.tick();
        }

        if (enemy_killed >= enemy_count) {
            playerEntity.clear(); // clearing all bullets from the list
            enemyEntity.clear(); // clearing all enemy group from the list
            enemy_count += 2;    //enemy added for new level
            enemy_killed = 0;    //reseting enemy_killed back to 0 for that level
            level++;        //each cycle will result new level
            controller.createEnemy(enemy_count);    //new level new enemies
        }

    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        g.drawImage(background, 30, ybg, null);
        g.drawImage(background, 30, ybg2, null);

        if (State == STATE.GAME) {
            if (level < 10) {
                g.drawImage(background, 0, ybg, null);
                g.drawImage(background, 0, ybg2, null);
            } else {
                g.drawImage(background1, 0, ybg, null);
                g.drawImage(background1, 0, ybg2, null);
            }
            player.render(g);
            controller.render(g);

            int alpha = 150;
            Color grey = new Color(224, 224, 224, alpha);
            Color green = new Color(0, 255, 0, alpha);
            Color white = new Color(255, 225, 225, alpha);

            g.setColor(grey);
            g.fillRect(5, 5, 200, 20);

            g.setColor(green);
            g.fillRect(5, 5, HEALTH, 20);

            g.setColor(white);
            g.drawRect(5, 5, 200, 20);
            g.drawString("Life : " + totalContinue, 5, 40);

            Font fnt0 = new Font(GameHelper.FONT_ARIAL, Font.BOLD, 13);
            g.setFont(fnt0);
            g.setColor(Color.white);
            g.drawString("Score : " + getScore(), GameHelper.WIDTH * 2 - 100, 20);

            fnt0 = new Font(GameHelper.FONT_MONACO, Font.BOLD, 20);
            g.setFont(fnt0);
            g.drawString("Level : " + String.valueOf(level), GameHelper.WIDTH - 30, 20);
        } else if (State == STATE.MENU) {
            menu.render(g);
        } else if (State == STATE.HELP) {
            Font fnt0 = new Font(GameHelper.FONT_ARIAL, Font.BOLD, 59);
            g.setFont(fnt0);
            g.setColor(Color.white);
            g.drawString(GameHelper.TITLE, GameHelper.WIDTH / 4, 100);

            Font fnt1 = new Font(GameHelper.FONT_IMPACT, Font.BOLD, 22);
            g.setFont(fnt1);

            g.drawString("Up: 		Up Arrow", (GameHelper.WIDTH / 2) + 100, 150);
            g.drawString("Down: 	Down Arrow", (GameHelper.WIDTH / 2) + 100, 200);
            g.drawString("Left: 	Left Arrow", (GameHelper.WIDTH / 2) + 100, 250);
            g.drawString("Right: 	Right Arrow", (GameHelper.WIDTH / 2) + 100, 300);
            g.drawString("Shoot: 	Space", (GameHelper.WIDTH / 2) + 50, 350);
            g.drawString("Pause: 	ESC", (GameHelper.WIDTH / 2) + 200, 350);

            Graphics2D g2d = (Graphics2D) g;
            Rectangle backButton = new Rectangle(GameHelper.WIDTH / 2 + 120, 380, 100, 50);
            g.drawString(GameHelper.GO_BACK_MENU, backButton.x + 19, backButton.y + 30);
            g2d.draw(backButton);
        } else if (State == STATE.GAMEOVER) {
            Graphics2D g2d = (Graphics2D) g;
            Rectangle restartGame = new Rectangle(GameHelper.WIDTH / 2 + 120, 250, 100, 50);
            Rectangle continueGame = new Rectangle(GameHelper.WIDTH / 2 + 120, 150, 100, 50);

            Font fnt0 = new Font(GameHelper.FONT_ARIAL, Font.BOLD, 59);
            g.setFont(fnt0);
            g.setColor(Color.white);
            g.drawString("GAME OVER", GameHelper.WIDTH / 2, 100);

            fnt0 = new Font(GameHelper.FONT_ARIAL, Font.BOLD, 15);
            g.setFont(fnt0);

            g.drawString("Life : " + totalContinue, continueGame.x + 19, continueGame.y + 30);
            g2d.draw(continueGame);
            g.drawString("Main Menu", restartGame.x + 15, restartGame.y + 30);
            g2d.draw(restartGame);

            fnt0 = new Font(GameHelper.FONT_ARIAL, Font.BOLD, 30);
            g.setFont(fnt0);
            g.drawString("Your Score : " + score, restartGame.x - 50, restartGame.y + 100);

            fnt0 = new Font(GameHelper.FONT_ARIAL, Font.BOLD, 35);
            g.setFont(fnt0);

            try {
                g.drawString("High Score : " + highScore.getScore(), restartGame.x - 120, restartGame.y + 150);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (HEALTH <= 0) {
            State = STATE.GAMEOVER;
            HEALTH = 200;
            player.setVelX(0);
            player.setVelY(0);

            try {
                if (highScore.getScore() < score) {
                    highScore.setScore(score);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (State == STATE.RESTART) {
            score = 0; // to reset score after game over
            level = 1;    // default level
            enemy_count = 8; // to reset enemy count after game over
            enemy_killed = 0; // to reset enemy killed after game over
            totalContinue = 3; // Restarting life back to 3
            playerEntity.clear(); // clearing all bullets from the list
            enemyEntity.clear(); // clearing all enemy group from the list
            controller.createEnemy(enemy_count); //Creating new enemies to start the game
            State = STATE.MENU;
            HEALTH = 200;    // Default health
        }

        g.dispose();
        bs.show();

    }

    public void keyPressed(KeyEvent e) throws LineUnavailableException, IOException {
        int key = e.getKeyCode();
        if (State == STATE.GAME) {
            if (key == KeyEvent.VK_RIGHT) {
                player.setVelX(4 + level * 0.5);
            } else if (key == KeyEvent.VK_LEFT) {
                player.setVelX(-4 - level * 0.5);
            } else if (key == KeyEvent.VK_DOWN) {
                player.setVelY(4 + level * 0.5);
            } else if (key == KeyEvent.VK_UP) {
                player.setVelY(-4 - level * 0.5);
            } else if (key == KeyEvent.VK_SPACE && !is_shooting) {
                if (level >= 1 && level <= 9) {
                    controller.addEntity(new Bullet1(player.getX(), player.getY(), texture, this));    //Creating bullet for each space
                } else if (level > 9 && level <= 15) {
                    controller.addEntity(new Bullet2(player.getX(), player.getY(), texture, this));    //Creating bullet for each space
                    controller.addEntity(new Bullet3(player.getX(), player.getY(), texture, this));    //Creating bullet for each space
                } else {
                    controller.addEntity(new Bullet1(player.getX(), player.getY(), texture, this));    //Creating bullet for each space
                    controller.addEntity(new Bullet2(player.getX(), player.getY(), texture, this));    //Creating bullet for each space
                    controller.addEntity(new Bullet3(player.getX(), player.getY(), texture, this));    //Creating bullet for each space
                }
                sound.playGunSound();            //Every space will produce gun sound
                is_shooting = true;
            } else if (key == KeyEvent.VK_ESCAPE) {
                State = STATE.MENU;        // Just go back to Menu (Pause the game)
            }
        } else if (State == STATE.MENU) {
            if (key == KeyEvent.VK_ESCAPE) {
                State = STATE.GAME;    // start new game or go back to game if it was paused
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (State == STATE.GAME) {
            if (key == KeyEvent.VK_RIGHT) {
                player.setVelX(0);
            } else if (key == KeyEvent.VK_LEFT) {
                player.setVelX(0);
            } else if (key == KeyEvent.VK_DOWN) {
                player.setVelY(0);
            } else if (key == KeyEvent.VK_UP) {
                player.setVelY(0);
            } else if (key == KeyEvent.VK_SPACE) {
                sound.stopGunSound();
                is_shooting = false;
            }
        }
    }

    public void setEnemy_count(int enemy_count) {
        this.enemy_count = enemy_count;
    }

    public void setEnemy_killed(int enemy_killed) {
        this.enemy_killed = enemy_killed;
    }

    public int getEnemy_killed() {
        return enemy_killed;
    }

    public Player getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public BufferedImage getSpriteSheet() {
        return spriteSheet;
    }

    public Sound getSound() {
        return sound;
    }

    public int getLevel() {
        return level;
    }

    public List<BasePlayer> getPlayerEntity() {
        return playerEntity;
    }

    public List<BaseEnemy> getEnemyEntity() {
        return enemyEntity;
    }

    public BufferedImage getPlayerBuffer() {
        return mainPlayer;
    }

    public int getEnemy_count() {
        return enemy_count;
    }

    public static int getTotalContinue() {
        return totalContinue;
    }

    public static void setTotalContinue(int totalContinue) {
        Game.totalContinue = totalContinue;
    }

    public void updateScore(int x) {
        this.score += x;
    }
}
