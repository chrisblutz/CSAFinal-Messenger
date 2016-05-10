package com.github.chrisblutz.messenger.ui;

import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;


/**
 * @author Christopher Lutz
 */
public class FilledRoundedBorder extends AbstractBorder {

    private Color color;
    private int thickness;
    private int radius;
    private Insets insets;
    private BasicStroke stroke;
    private int strokePad;
    private RenderingHints hints;

    public FilledRoundedBorder(Color color, int thickness, int radius) {

        this.color = color;
        this.thickness = thickness;
        this.radius = radius;

        stroke = new BasicStroke(thickness);
        strokePad = thickness / 2;

        hints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int pad = radius + strokePad;
        insets = new Insets(pad, pad, pad, pad);
    }

    @Override
    public Insets getBorderInsets(Component c) {

        return insets;
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {

        return getBorderInsets(c);
    }

    @Override
    public void paintBorder(Component c, Graphics graphics, int x, int y, int width, int height) {

        Graphics2D g = (Graphics2D) graphics;
        g.setRenderingHints(hints);

        RoundRectangle2D.Double rect = new RoundRectangle2D.Double((double) strokePad, (double) strokePad, width - thickness, height - thickness, radius, radius);
        Area area = new Area(rect);

        Component parent = c.getParent();

        if (parent != null) {

            Color background = parent.getBackground();
            Rectangle fillRect = new Rectangle(0, 0, width, height);
            Area fillArea = new Area(fillRect);
            fillArea.subtract(area);
            g.setClip(fillArea);
            g.setColor(background);
            g.fillRect(0, 0, width, height);
            g.setClip(null);
        }

        g.setColor(color);
        g.setStroke(stroke);
        g.draw(area);
    }
}
