package org.af.commons.widgets.animated;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Based on Code from Romain Guy from the book Swing Hacks (by Joshua Marinacci, Chris Adamson) subject to the BSD license.
 * Copyright (c) 2005, Romain Guy (romain.guy@jext.org); modifications for this library by Bernd Bischl and Kornelius Rohmeyer
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *     * Neither the name of the author nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

public class Star extends Area implements AnimatedObject {
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