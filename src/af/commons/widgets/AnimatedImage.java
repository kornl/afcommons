package af.commons.widgets;

import java.awt.Graphics2D;

public class AnimatedImage implements AnimatedObject {

	protected String name;
	
	public AnimatedImage (String name) {
		this.name = name;
	}
	
	public double getMaxY() {
		return 0;
	}

	@Override
	public void paint(Graphics2D g2, int alphaLevel, int i, int j) {
		
	}

	@Override
	public void next() {
		
	}

}
