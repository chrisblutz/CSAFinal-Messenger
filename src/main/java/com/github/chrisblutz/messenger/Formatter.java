package com.github.chrisblutz.messenger;

import java.awt.*;


/**
 * @author Christopher Lutz
 */
public class Formatter {

    public static String formatColorAsHexadecimal(Color color){

        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
}
