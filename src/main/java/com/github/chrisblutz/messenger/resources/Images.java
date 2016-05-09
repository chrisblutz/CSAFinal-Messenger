package com.github.chrisblutz.messenger.resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;


/**
 * @author Christopher Lutz
 */
public class Images {

    public static BufferedImage loadInternal(String name) {

        InputStream is = Images.class.getResourceAsStream("/res/images/" + name + ".png");

        if (is != null) {

            try {

                return ImageIO.read(is);

            } catch (Exception e) {

                e.printStackTrace();

                return null;
            }

        } else {

            return null;
        }
    }
}
