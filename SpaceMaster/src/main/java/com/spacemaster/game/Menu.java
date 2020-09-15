package com.spacemaster.game;

import com.spacemaster.game.utils.GameHelper;

import java.awt.*;

public class Menu {

    public Rectangle playButton = new Rectangle(GameHelper.WIDTH / 2 + 120, 150, 100, 50);
    public Rectangle helpButton = new Rectangle(GameHelper.WIDTH / 2 + 120, 250, 100, 50);
    public Rectangle quitButton = new Rectangle(GameHelper.WIDTH / 2 + 120, 350, 100, 50);
    public Rectangle mute = new Rectangle(GameHelper.WIDTH / 4, 370, 30, 30);

    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Font fnt0 = new Font(GameHelper.FONT_ARIAL, Font.BOLD, 59);
        g.setFont(fnt0);
        g.setColor(Color.white);
        g.drawString(GameHelper.TITLE, Game.WIDTH / 4, 100);

        fnt0 = new Font(GameHelper.FONT_ARIAL, Font.BOLD, 30);
        g.setFont(fnt0);
        g.drawString(GameHelper.PLAY_TITLE, playButton.x + 19, playButton.y + 30);
        g2d.draw(playButton);
        g.drawString(GameHelper.HELP_TITLE, helpButton.x + 19, helpButton.y + 30);
        g2d.draw(helpButton);
        g.drawString(GameHelper.QUIT_TITLE, quitButton.x + 19, quitButton.y + 30);
        g2d.draw(quitButton);

        //Mute
        fnt0 = new Font(GameHelper.FONT_ARIAL, Font.BOLD, 20);
        g.setFont(fnt0);
        g.drawString(GameHelper.MUSIC, mute.x + 5, mute.y + 20);
        g2d.draw(mute);

    }

}
