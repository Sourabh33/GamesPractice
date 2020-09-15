package com.spacemaster.game.controlinputs;

import com.spacemaster.game.Game;
import com.spacemaster.game.pojo.STATE;
import com.spacemaster.game.utils.GameHelper;
import com.spacemaster.game.utils.Sound;

import javax.sound.sampled.LineUnavailableException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseInput implements MouseListener {

    private final Sound sound;

    public MouseInput(Sound sound) {
        this.sound = sound;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX();
        int my = e.getY();

        if(Game.State == STATE.MENU) {
            if (mx >= GameHelper.WIDTH / 2 + 120 && mx <= GameHelper.WIDTH / 2 + 220) {
                if (my >= 150 && my <= 200) {
                    Game.State = STATE.GAME;
                }
            }
            // Menu Button
            if (mx >= GameHelper.WIDTH / 2 + 120 && mx <= GameHelper.WIDTH / 2 + 220) {
                if (my >= 250 && my <= 300) {
                    Game.State = STATE.HELP;
                }
            }
            // Quit Button
            if (mx >= GameHelper.WIDTH / 2 + 120 && mx <= GameHelper.WIDTH / 2 + 220) {
                if (my >= 350 && my <= 400) {
                    System.exit(1);
                }
            }

            // Mute Button
            if (mx >= GameHelper.WIDTH / 4 && mx <= GameHelper.WIDTH / 4 + 50) {
                if (my >= 350 && my <= 400) {
                    if (Sound.gameMusicPlaying) {
                        sound.stopGameMusic();
                    } else {
                        try {
                            sound.playGameMusic();
                        } catch (LineUnavailableException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }

        if (Game.State == STATE.HELP) {
            // Back Button
            if (mx >= GameHelper.WIDTH / 2 + 120 && mx <= GameHelper.WIDTH / 2 + 220) {
                if (my >= 380 && my <= 430) {
                    Game.State = STATE.MENU;
                }
            }
        }

        if (Game.State == STATE.GAMEOVER) {
            // Continue Button
            if ((mx >= GameHelper.WIDTH / 2 + 120 && mx <= GameHelper.WIDTH / 2 + 220) && !(Game.getTotalContinue() <= 0)) {
                if (my >= 150 && my <= 200) {
                    Game.State = STATE.GAME;
                    Game.setTotalContinue(Game.getTotalContinue() - 1);
                }
            } // Main Menu Button
            if (mx >= GameHelper.WIDTH / 2 + 120 && mx <= GameHelper.WIDTH / 2 + 220) {
                if (my >= 250 && my <= 300) {
                    Game.State = STATE.RESTART;
                }
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
