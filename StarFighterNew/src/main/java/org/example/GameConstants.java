package org.example;

/**
 * Central place for all tunable game values.
 * Change numbers here instead of hunting through every class.
 */
public final class GameConstants {

    private GameConstants() {
        // prevent instantiation
    }

    // Window
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    // Fighter
    public static final int FIGHTER_WIDTH = 50;
    public static final int FIGHTER_HEIGHT = 50;
    public static final int FIGHTER_SPEED = 5;
    public static final int FIGHTER_START_HEALTH = 100;

    // Aliens
    public static final int ALIEN_WIDTH = 40;
    public static final int ALIEN_HEIGHT = 40;
    public static final int ALIEN_BASE_SPEED = 2;
    public static final int ALIEN_START_HEALTH = 3;
    public static final int NUM_ALIENS = 5;

    // Game loop
    public static final int TIMER_DELAY_MS = 16; // ~60 FPS
}
