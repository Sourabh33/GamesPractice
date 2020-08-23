package com.fighter.star.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static com.fighter.star.game.GameConstant.*;

public class OuterSpace extends Canvas implements KeyListener, Runnable {
    private SpaceShip spaceShip;
    private Alien alienOne;
    private Alien alienTwo;

    private ArrayList<Alien> aliens;
    private ArrayList<Ammo> ammoShots;

    private boolean[] keys;
    private BufferedImage back;

    // 2 sec
    private final int ALIEN_ATTACK = 2000;
    private Timer addAlienTimer;

    public OuterSpace() {
        setBackground(Color.BLACK);
        keys = new boolean[5];

        spaceShip = new SpaceShip(400, 450, 5);
        aliens = new ArrayList<>();
        aliens.add(new Alien(250, 50, 2));
        aliens.add(new Alien(450, 50, 2));
        aliens.add(new Alien(650, 50, 2));
        aliens.add(new Alien(850, 50, 2));

        ammoShots = new ArrayList<>();
        this.addKeyListener(this);
        new Thread(this).start();

        setVisible(true);
        startAttack();
        moreAliens();
    }

    @Override
    public void update(Graphics g) {
        paint(g);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D twoDgraph = (Graphics2D) g;

        if (back == null)
            back = (BufferedImage) createImage(getWidth(), getHeight());

        Graphics graphToBack = back.createGraphics();
        graphToBack.setColor(Color.BLUE);
        graphToBack.drawString("StarFighter", 25, 50);
        graphToBack.setColor(Color.BLACK);
        graphToBack.fillRect(0, 0, 800, 600);

        if (keys[0]) {
            if (spaceShip.getX() > 10) {
                spaceShip.move(DIR_LEFT);
            }
        }
        if (keys[1]) {
            if (spaceShip.getX() < 700) {
                spaceShip.move(DIR_RIGHT);
            }
        }
        if (keys[2]) {
            if (spaceShip.getY() > 10) {
                spaceShip.move(DIR_DOWN);
            }
        }
        if (keys[3]) {
            if (spaceShip.getY() < 500) {
                spaceShip.move(DIR_UP);
            }
        }
        if (keys[4]) {
            Ammo shot = new Ammo(spaceShip.getX() + 28, spaceShip.getY(), 5);
            ammoShots.add(shot);
            keys[4] = false;
        }

        for (Alien a : aliens) {
            a.draw(graphToBack);
            if (a.getX() <= 1000) {
                if (a.getX() > 900)
                    a.setX(-20);
            }
            a.move(DIR_RIGHT);

            for (Ammo s : ammoShots) {
                if (a.getX() >= s.getX() && a.getX() <= s.getX() + 100 && a.getY() >= s.getY() && a.getY() <= s.getY() + 80) {
                    aliens.remove(a);
                    ammoShots.remove(s);
                }
            }
        }

        for (Ammo s : ammoShots) {
            s.draw(graphToBack);
            s.move(DIR_DOWN);
        }

        ammoShots.removeIf(s -> s.getY() <= 0);

        spaceShip.draw(graphToBack);
        twoDgraph.drawImage(back, null, 0, 0);
    }

    private void moreAliens() {
        int x = (int) (Math.random() * getWidth());
        int y = (int) (Math.random() * (getHeight() - 200));

        if (addAlienTimer.isRunning()) {
            aliens.add(new Alien(x, y, 2));
            System.out.println("new Alien!" + " " + x + " " + y);
        }
    }

    private void startAttack() {
        if (addAlienTimer == null) {

            addAlienTimer = new Timer(ALIEN_ATTACK, new OuterSpace.TimerHandler());
            addAlienTimer.start();

        } else {
            if (!addAlienTimer.isRunning()) {
                addAlienTimer.restart();

            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys[0] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keys[1] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            keys[2] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keys[3] = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            keys[4] = true;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            keys[0] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            keys[1] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            keys[2] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            keys[3] = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            keys[4] = false;
        }
        repaint();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Thread.sleep(5);
                repaint();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private class TimerHandler implements ActionListener {
        public void actionPerformed(ActionEvent actionEvent) {
            moreAliens();
        }
    }
}
