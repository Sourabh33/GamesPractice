package com.spacemaster.game.utils;

import com.spacemaster.game.Game;

import java.awt.image.BufferedImage;

public class Texture {
    BufferedImageLoader loader;
    public BufferedImage mainPlayer;
    public BufferedImage[] missile = new BufferedImage[5];
    public BufferedImage[] enemy = new BufferedImage[6];
    public BufferedImage[] asteroid = new BufferedImage[3];
    public BufferedImage[] players = new BufferedImage[4];

    private final SpriteSheet spriteSheet;
    private final Game game;

    public Texture(Game game) {
        this.spriteSheet = new SpriteSheet(game.getSpriteSheet());
        this.game = game;
        this.loader = new BufferedImageLoader();
        getTextures();
    }

    private void getTextures() {
        this.asteroid[0] = loader.loadImage(GameHelper.ASTEROID);
        this.enemy[0] = loader.loadImage(GameHelper.ENEMY1);
        this.enemy[1] = loader.loadImage(GameHelper.ENEMY2);
        this.enemy[2] = loader.loadImage(GameHelper.ENEMY3);
        this.enemy[3] = loader.loadImage(GameHelper.ENEMY4);
        this.enemy[4] = loader.loadImage(GameHelper.ENEMY6);
        this.enemy[5] = loader.loadImage(GameHelper.ENEMY7);

        this.missile[0] = loader.loadImage(GameHelper.AMMO1);
        this.missile[1] = loader.loadImage(GameHelper.AMMO2);
        this.missile[2] = loader.loadImage(GameHelper.AMMO3);
        this.missile[3] = loader.loadImage(GameHelper.AMMO4);
        this.missile[4] = loader.loadImage(GameHelper.AMMO5);

        this.players[0] = loader.loadImage(GameHelper.MAIN_PLAYER1);
        this.players[1] = loader.loadImage(GameHelper.MAIN_PLAYER2);
        this.players[2] = loader.loadImage(GameHelper.MAIN_PLAYER3);
        this.players[3] = loader.loadImage(GameHelper.MAIN_PLAYER4);
        this.mainPlayer = loader.loadImage(GameHelper.MAIN_PLAYER1);
    }


}
