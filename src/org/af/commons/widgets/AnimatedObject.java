package org.af.commons.widgets;

import java.awt.Graphics2D;

public interface AnimatedObject {

	void paint(Graphics2D g2, int alphaLevel, int i, int j);

	double getMaxY();

	void next();

}
