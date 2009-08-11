package org.af.commons.widgets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

class Star extends Area implements AnimatedObject {
    private Area[] ticker = null;
    int alphaLevel = 0;
    double fixedAngle;
    AffineTransform rotator;
    Point center;


    public Star(int barsCount) {
        center = new Point(0, 0);
        ticker = new Area[barsCount];
        fixedAngle = 2.0 * Math.PI / ((double) barsCount);

        for (int i = 0; i < barsCount; i++) {
            Area t = buildPrimitive();
            AffineTransform toBorder = AffineTransform.getTranslateInstance(45.0, -6.0);

            t.transform(toBorder);
            t.transform(getRotator(i));
            ticker[i] = t;
        }
    }

    public int getBarsCount() {
        return ticker.length;
    }

    private AffineTransform getRotator(int i) {
        return AffineTransform.getRotateInstance(i * fixedAngle, center.getX(), center.getY());
    }

    private Area buildPrimitive() {
        Rectangle2D.Double body = new Rectangle2D.Double(6, 0, 30, 12);
        Ellipse2D.Double head = new Ellipse2D.Double(0, 0, 12, 12);
        Ellipse2D.Double tail = new Ellipse2D.Double(30, 0, 12, 12);

        Area tick = new Area(body);
        tick.add(new Area(head));
        tick.add(new Area(tail));

        return tick;
    }

    public synchronized void next() {
        for (Area t : ticker)
            t.transform(getRotator(1));
    }

    public synchronized void moveTo(int x, int y) {
        AffineTransform move = AffineTransform.getTranslateInstance(x - center.x, y - center.y);

        for (Area t : ticker) {
            t.transform(move);
        }
        center = new Point(x, y);
    }

    public synchronized void paint(Graphics2D g2, int alphaLevel, int x, int y) {
        moveTo(x, y);
        for (int i = 0; i < getBarsCount(); i++) {
            int b = getBarsCount()/2;
            int channel = 224 - 128 / (Math.abs( (b/2 - i)*(b/2 - i) ) +1);
            g2.setColor(new Color(channel, channel, channel, alphaLevel));
            g2.fill(ticker[i]);
        }
    }

    public synchronized double getMaxY() {
        double maxY = 0;
        for (Area t : ticker) {
            Rectangle2D bounds = t.getBounds2D();
            if (bounds.getMaxY() > maxY)
                maxY = bounds.getMaxY();
        }
        return maxY;
    }
}