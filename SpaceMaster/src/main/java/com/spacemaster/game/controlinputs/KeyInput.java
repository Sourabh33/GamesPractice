package com.spacemaster.game.controlinputs;

import com.spacemaster.game.Game;

import javax.sound.sampled.LineUnavailableException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class KeyInput extends KeyAdapter {

    Game game;

    public KeyInput(Game game) {
        this.game = game;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            game.keyPressed(e);
        } catch (LineUnavailableException | IOException e1) {
            e1.printStackTrace();
        }
    }

    public void keyReleased(KeyEvent e) {
        game.keyReleased(e);
    }
}
