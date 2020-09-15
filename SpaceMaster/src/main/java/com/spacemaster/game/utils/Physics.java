package com.spacemaster.game.utils;

import com.spacemaster.game.pojo.BasePlayer;
import com.spacemaster.game.pojo.BaseEnemy;

public class Physics {
    // For player group colliding with enemy group
    public static boolean Collision(BasePlayer player, BaseEnemy enemy) {
        return player.getBounds().intersects(enemy.getBounds());
    }

    // For enemy group colliding with player group
    public static boolean Collision(BaseEnemy enemy, BasePlayer player) {
        return enemy.getBounds().intersects(player.getBounds());
    }
}
