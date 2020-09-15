package com.spacemaster.game.utils;

import javax.sound.sampled.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.io.IOException;
import java.net.URL;

public class Sound {
    private final AudioClip gun;
    private final AudioClip boom;
    private final AudioClip hit;

    private Clip gameClip;
    private AudioInputStream game;

    public static boolean gameMusicPlaying;

    public Sound() {
        gameMusicPlaying = false;
        URL laserSound = Sound.class.getResource(GameHelper.LASER_SOUND);
        URL boomSound = Sound.class.getResource(GameHelper.BOOM_SOUND);
        URL adventureSound = Sound.class.getResource(GameHelper.ADVENTURE_SOUND);
        URL hitSound = Sound.class.getResource(GameHelper.HIT_SOUND);

        gun = Applet.newAudioClip(laserSound);
        boom = Applet.newAudioClip(boomSound);
        hit = Applet.newAudioClip(hitSound);

        try {
            game = AudioSystem.getAudioInputStream(adventureSound);
        } catch (UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }

        try {
            gameClip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

        try {
            gameClip.open(game);
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void playGunSound() {
        gun.play();
    }

    public void stopGunSound() {
        gun.stop();
    }

    public void playHitSound() {
        hit.play();
    }

    public void stopHitSound() {
        hit.stop();
    }

    public void playExplosion() {
        boom.stop();
        boom.play();
    }

    public void stopExplosion() {
        boom.stop();
    }

    public void playGameMusic() throws LineUnavailableException {
        if (!gameMusicPlaying) {
            gameClip.loop(Clip.LOOP_CONTINUOUSLY);
            gameMusicPlaying = true;
        }
    }

    public void stopGameMusic() {
        gameClip.stop();
        gameMusicPlaying = false;
    }
}
