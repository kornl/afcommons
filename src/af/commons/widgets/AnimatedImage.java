package af.commons.widgets;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AnimatedImage implements AnimatedObject {

	protected String name;
	protected int max;
	protected int nr = 0;	
	protected double scale = 3;
	protected int width = 31;
	protected int height = 34;
	
	final static String juggle = "juggle"; 
	
	public AnimatedImage (String name, int max) {
		this.name = name;
		this.max = max;
	}
	
	public AnimatedImage (String type) {
		this.name = type;
		this.max = 17;
	}
	
	public double getMaxY() {
		return 200;
	}

	@Override
	public void paint(Graphics2D g2, int alphaLevel, int x, int y) {
		try {
			Image img = ImageIO.read(AnimatedImage.class.getResource("animated/"+name+"-"+nr+".png"));
			AffineTransform at = new AffineTransform(scale,0d,0d,scale,x-(width*scale)/2,y-(height*scale)/2);
			g2.drawImage(img, at, null);
			//g2.drawImage(img, x, y, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    		
	}

	@Override
	public void next() {
		nr++;
		if (nr>max) nr=0;
	}

}
