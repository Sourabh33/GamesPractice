package com.fighter.star.game;

import javax.swing.*;
import java.awt.*;

public class StarFighterGame extends JFrame {

    private static final int W_WIDTH = 800;
    private static final int W_HEIGHT = 600;

    public StarFighterGame() {
        super("Star-Fighter Game");
        setSize(W_WIDTH, W_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        initGame();
    }

    private void initGame() {
        OuterSpace outerSpace = new OuterSpace();
        outerSpace.setFocusable(true);
        getContentPane().add(outerSpace);
    }

    public static void main(String[] args) {
        System.out.println("Star Fighter Game");
        EventQueue.invokeLater(() -> {
            JFrame frame = new StarFighterGame();
            frame.setVisible(true);
        });
    }
}
