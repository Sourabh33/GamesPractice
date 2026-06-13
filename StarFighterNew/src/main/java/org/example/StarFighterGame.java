package org.example;

import javax.swing.*;
import java.awt.*;

public class StarFighterGame extends JFrame {
    public StarFighterGame() {
        super("Star-Fighter Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        OuterSpace outerSpace = new OuterSpace();
        outerSpace.setFocusable(true);
        getContentPane().add(outerSpace);

        pack(); // sizes the frame to fit OuterSpace's preferred size
        setLocationRelativeTo(null);

        // request focus once the window is actually shown,
        // otherwise key presses won't reach the OuterSpace panel
        outerSpace.requestFocusInWindow();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            StarFighterGame frame = new StarFighterGame();
            frame.setVisible(true);
        });
    }
}