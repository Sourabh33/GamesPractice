package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class OuterSpace extends JPanel implements ActionListener, KeyListener {
    private final Timer gameTimer;
    private final Random random = new Random();

    private final Fighter fighter;
    private final List<Alien> aliens = new ArrayList<>();
    private final List<Ammo> ammoInFlight = new ArrayList<>();

    private boolean movingLeft;
    private boolean movingRight;
    private int score;
    private boolean gameOver;

    public OuterSpace() {
        setPreferredSize(new Dimension(GameConstants.WIDTH, GameConstants.HEIGHT));
        setBackground(Color.BLACK);

        fighter = new Fighter(
                (GameConstants.WIDTH - GameConstants.FIGHTER_WIDTH) / 2,
                GameConstants.HEIGHT - GameConstants.FIGHTER_HEIGHT - 20
        );

        spawnAliens(GameConstants.NUM_ALIENS);

        addKeyListener(this);
        gameTimer = new Timer(GameConstants.TIMER_DELAY_MS, this);
        gameTimer.start();
    }

    /** Creates n aliens scattered near the top of the screen. */
    private void spawnAliens(int n) {
        for (int i = 0; i < n; i++) {
            int x = random.nextInt(GameConstants.WIDTH - GameConstants.ALIEN_WIDTH);
            int y = random.nextInt(200);
            aliens.add(new Alien(x, y));
        }
    }

    // ------------------------------------------------------------
    // Drawing
    // ------------------------------------------------------------

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        fighter.draw(g2d);
        for (Alien alien : aliens) {
            alien.draw(g2d);
        }
        for (Ammo ammo : ammoInFlight) {
            ammo.draw(g2d);
        }

        drawHud(g2d);
        if (gameOver) {
            drawGameOverMessage(g2d);
        }
    }

    private void drawHud(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Score: " + score, 10, 20);
        g2d.drawString("Health: " + fighter.getHealth(), 10, 40);
        g2d.drawString("Ammo: " + fighter.getCurrentAmmoType(), 10, 60);
        g2d.drawString("[1] Normal  [2] Laser  [SPACE] Fire", 10, GameConstants.HEIGHT - 10);
    }

    private void drawGameOverMessage(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        g2d.setFont(new Font("Arial", Font.BOLD, 48));
        String message = "GAME OVER";
        int textWidth = g2d.getFontMetrics().stringWidth(message);
        g2d.drawString(message, (GameConstants.WIDTH - textWidth) / 2, GameConstants.HEIGHT / 2);
    }

    // ------------------------------------------------------------
    // Game loop
    // ------------------------------------------------------------

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver) {
            return;
        }

        updateFighterPosition();
        updateAliens();
        updateAmmo();

        handleAmmoVsAlienCollisions();
        handleFighterVsAlienCollisions();

        if (!fighter.isAlive()) {
            gameOver = true;
            gameTimer.stop();
        }

        repaint();
    }

    private void updateFighterPosition() {
        if (movingLeft) {
            fighter.moveLeft();
        }
        if (movingRight) {
            fighter.moveRight();
        }
    }

    private void updateAliens() {
        for (Alien alien : aliens) {
            alien.update();
        }
    }

    private void updateAmmo() {
        for (Ammo ammo : ammoInFlight) {
            ammo.update();
        }
        ammoInFlight.removeIf(ammo -> !ammo.isActive());
    }

    private void handleAmmoVsAlienCollisions() {
        for (Ammo ammo : ammoInFlight) {
            if (!ammo.isActive()) {
                continue;
            }
            for (Alien alien : aliens) {
                if (ammo.getBounds().intersects(alien.getBounds())) {
                    alien.hit(ammo.getDamage());
                    ammo.deactivate();
                    if (alien.isDestroyed()) {
                        score += 10;
                    }
                    break; // this bullet is used up, move to the next one
                }
            }
        }
    }

    private void handleFighterVsAlienCollisions() {
        for (Alien alien : aliens) {
            if (fighter.getBounds().intersects(alien.getBounds())) {
                fighter.takeDamage(1);
                alien.respawnAtTop();
            }
        }
    }

    // ------------------------------------------------------------
    // Input
    // ------------------------------------------------------------

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                movingLeft = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                movingRight = true;
                break;
            case KeyEvent.VK_SPACE:
                if (!gameOver) {
                    ammoInFlight.add(fighter.fire());
                }
                break;
            case KeyEvent.VK_1:
                fighter.switchAmmo(AmmoType.NORMAL);
                break;
            case KeyEvent.VK_2:
                fighter.switchAmmo(AmmoType.LASER);
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> movingLeft = false;
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> movingRight = false;
            default -> {
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // not needed, but required by KeyListener
    }
}
