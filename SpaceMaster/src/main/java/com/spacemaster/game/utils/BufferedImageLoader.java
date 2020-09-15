package com.spacemaster.game.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class BufferedImageLoader {
    private static final int IMG_HEIGHT = 30;
    private static final int IMG_WIDTH = 30;
    private BufferedImage image = null;

    public BufferedImage loadImage(String imageName) {
        try {
            System.out.println("imageName: "+ imageName);
            URL resource = getClass().getResource(imageName);
            System.out.println("imageName: "+ resource.getFile());
            image = resizeImage(ImageIO.read(resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    private BufferedImage resizeImage(BufferedImage image) {
        System.out.println("image: "+ image);
        if(image == null) return null;
        Image tmp = image.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_SMOOTH);
        BufferedImage dmig = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = dmig.createGraphics();
        graphics2D.drawImage(tmp, 0,0,null);
        graphics2D.dispose();

        return dmig;
    }
}
