package com.spacemaster.game;

import javax.swing.*;
import java.awt.*;

import static com.spacemaster.game.utils.GameHelper.*;

public class StartClass {
    public static void main(String[] args) {
        Game g = new Game();
//        g.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
//        g.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
//        g.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

        JFrame f = new JFrame(TITLE);
        f.add(g);
        f.pack();
        f.setSize(WIDTH * SCALE, HEIGHT * SCALE);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        g.start();
    }
}
