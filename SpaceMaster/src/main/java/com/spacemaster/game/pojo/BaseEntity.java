package com.spacemaster.game.pojo;

import java.awt.*;

public interface BaseEntity {

    void tick();

    void render(Graphics g);

    Rectangle getBounds();

    double getX();

    double getY();
}
