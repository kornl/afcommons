package af.commons.widgets;

import static af.commons.widgets.DesktopPaneBG.LayoutType.HORIZONTAL;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * JDesktopPane with optional background.
 */
public class DesktopPaneBG extends JDesktopPane {
	public static final long serialVersionUID = 1L;
    private static final Log logger = LogFactory.getLog(DesktopPaneBG.class);
    
	private BufferedImage img = null;

    // trafo for resizing
    private AffineTransform at;
    // for resizing trafo
    private int oldwidth=0;
    private int oldheight=0;

    protected enum LayoutType {HORIZONTAL, VERTICAL, BLOCK}
    
    private final LayoutType layout = HORIZONTAL;


    public DesktopPaneBG() {
        this(null);
    }
    
	/**
	 * Standard Constructor with String specifying the image resource. 
	 * @param resource specifying the image resource
	 */
	public DesktopPaneBG(String resource) {
		if (resource==null) return;
		try {
			img = ImageIO.read(DesktopPaneBG.class.getResource(resource));
		} catch (Exception e) {
			logger.error("Error while loading background: "+e.getMessage(),e);
			throw new RuntimeException("Error while loading background", e);
		}
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double scale = Math.max(screenSize.width/(double)img.getWidth(), screenSize.height/(double)img.getHeight());
		logger.debug("Skale with factor "+scale+".");
		at = new AffineTransform(scale,0d,0d,scale,0d,0d);		
	}


    protected void paintComponent(Graphics g) {
    	Graphics2D g2d = (Graphics2D)g;
    	if (img==null) {
    		return;
    	}
    	if (getWidth()!= oldwidth || getHeight()!= oldheight) {    		
    		oldwidth = getWidth();
    		oldheight = getHeight();
    		double scale = Math.max(oldwidth/(double)img.getWidth(), oldheight/(double)img.getHeight());
    		logger.info("Skale with factor "+scale+".");
    		at = new AffineTransform(scale,0d,0d,scale,0d,0d);
    	}
    	g2d.drawImage(img, at, null);
    }
    
    public void addInternalFrame(JInternalFrame frame) {
    	add(frame);
    	try {
			frame.setSelected(true);
		} catch (java.beans.PropertyVetoException e) {
			logger.info("Could not set focus.");
		}
		frame.setVisible(true);
    }

    public void removeAllInternalFrames() {
        removeAll();
    }

    public void removeAllInternalDataFrames() {
    	for (JInternalFrame frame : getAllFrames()) {
    		if (frame instanceof JInternalFrame) {
    			remove(frame);
    		}
    	}
    }
    

	/**
	 * Arrange internal windows specified by layout.
	 */	
	public void arrange() {
		JInternalFrame[] frames = getAllFrames();
        Dimension desktopsize = getSize();
        int size = frames.length;
        if (layout == HORIZONTAL) {
        	for (int i = 0; i < size ; i++) {
    			JInternalFrame frame = frames[i];
    			//logger.debug("Insets: ("+i*(desktopsize.width / size)+","+0+")");
    			//logger.debug("Size: ("+desktopsize.width / size+","+desktopsize.height+")");
    			frame.setBounds(i*(desktopsize.width / size), 0,
    	        			desktopsize.width / size,
    	        			desktopsize.height);			 
    		}    		
		} else if (layout == HORIZONTAL) {
			for (int i = 0; i < size ; i++) {
    			JInternalFrame frame = frames[i];
    			//logger.debug("Insets: ("+i*(desktopsize.width / size)+","+0+")");
    			//logger.debug("Size: ("+desktopsize.width / size+","+desktopsize.height+")");
    			frame.setBounds(0, i*(desktopsize.height / size),
    	        			desktopsize.width,
    	        			desktopsize.height / size);			 
    		}
		} else { /* layout = BLOCK */
			
		}
	
	}
}
