package org.af.commons.widgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

import org.af.commons.widgets.animated.AnimatedImage;
import org.af.commons.widgets.animated.AnimatedObject;
import org.af.commons.widgets.animated.Star;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Panel that shows some animated symbol for an arbitrary long process.
 */
public class InfiniteProgressPanel extends JComponent implements MouseListener, KeyListener {
	private static final Log logger = LogFactory.getLog(InfiniteProgressPanel.class);

	public interface AbortListener {
        public void abort();
    }

    protected AnimatedObject animatedObject = null;

    protected static final int FADE_DELAY = 200;
    protected static final float SHIELD = 0.70f;
    protected static final int BARS_COUNT = 20;
    protected static final float FPS = 15.0f;

    protected int fadeDelay;
    protected float shield;
    protected String text = "";
    protected int barsCount;
    protected float fps;

    protected int alphaLevel = 0;
    protected int delay;
    protected Timer timer;
    protected Animator anim = null;

    protected RenderingHints hints = null;

    protected JFrame frame;
    protected Component defaultGlassPane;
    protected AbortListener abortListener;

    protected boolean interruptable = true;

    enum State {FADE_IN, MAIN, FADE_OUT, DONE}

    public InfiniteProgressPanel(JFrame frame) {
        this(frame, "");
    }

    public InfiniteProgressPanel(JFrame frame, String text) {
        this(frame, text, BARS_COUNT);
    }

    public InfiniteProgressPanel(JFrame frame, String text, int barsCount) {
        this(frame, text, barsCount, SHIELD);
    }

    public InfiniteProgressPanel(JFrame frame, String text, int barsCount, float shield) {
        this(frame, text, barsCount, shield, FPS);
    }

    public InfiniteProgressPanel(JFrame frame, String text, int barsCount, float shield, float fps) {
        this(frame, text, barsCount, shield, fps, FADE_DELAY);
    }

    public InfiniteProgressPanel(JFrame frame, String text, int barsCount, float shield, float fps, int fadeDelay) {
        this.frame = frame;
        defaultGlassPane = frame.getGlassPane();
        this.text = text;
        this.fadeDelay = fadeDelay >= 0 ? fadeDelay : 0;
        this.shield = shield >= 0.0f ? shield : 0.0f;
        this.fps = fps > 0.0f ? fps : 15.0f;
        this.barsCount = barsCount > 0 ? barsCount : 14;
        delay = (int) (1000 / fps);

        this.hints = new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        this.hints.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.hints.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    }

    public void setText(String text) {
        repaint();
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void start() {
        frame.getContentPane().setEnabled(false);
        frame.setGlassPane(this);
        addMouseListener(this);
        addKeyListener(this);
        setVisible(true);
        requestFocus();
        if (animatedObject==null) animatedObject = new Star(barsCount);
        anim = new Animator(true);
        timer = new Timer(delay, anim);
        timer.start();
    }
    
    public void setAnimatedObject(Object o) {
    	if (o instanceof AnimatedObject) {
    		animatedObject = (AnimatedObject)o;
    	} else if (o instanceof String) {
    		if (o.equals("Juggler")) {
    			animatedObject = new AnimatedImage(AnimatedImage.JUGGLE);
    		} else if (o.equals("StarWars")) {
    			//animatedObject = new AnimatedImage(AnimatedImage.juggle);
    		}		
    	}
    }
    
    public void setInterruptable(boolean b) {
    	
    }

    public void stop() {
        if (timer != null) {
            timer.stop();
        }
        exit();

//            anim = new Animator(false);
//            timer = new Timer(delay, anim);
//            timer.start();
    }

    private void exit() {
        setVisible(false);
        removeMouseListener(this);
        removeKeyListener(this);
        frame.setGlassPane(defaultGlassPane);
        frame.getContentPane().setEnabled(true);
    }

    public void interrupt() {
        timer.stop();
        exit();
        abortListener.abort();
    }

    public void addAbortListener(AbortListener listener) {
        abortListener = listener;
    }

    public void paintComponent(Graphics g) {
    	int width = getWidth();
    	int height = getHeight();


    	Graphics2D g2 = (Graphics2D) g;
    	g2.setRenderingHints(hints);

    	g2.setColor(new Color(255, 255, 255, (int) (alphaLevel * shield)));
    	g2.fillRect(0, 0, getWidth(), getHeight());

    	if (animatedObject!=null) {
    		animatedObject.paint(g2, alphaLevel, width / 2, height / 2);
    		double maxY = animatedObject.getMaxY();

    		String text1 = text;
    		String text2 = "[Hit Esc to abort]";
    		if (text != null && text.length() > 0) {
    			FontRenderContext context = g2.getFontRenderContext();
    			TextLayout layout1 = new TextLayout(text1, getFont(), context);
    			TextLayout layout2 = new TextLayout(text2, getFont(), context);
    			Rectangle2D bounds1 = layout1.getBounds();
    			Rectangle2D bounds2 = layout2.getBounds();

    			g2.setColor(getForeground());
    			layout1.draw(g2, (float) (width - bounds1.getWidth()) / 2,
    					(float) (maxY + layout1.getLeading() + 2 * layout1.getAscent()));
    			layout2.draw(g2, (float) (width - bounds2.getWidth()) / 2,
    					(float) (maxY + 2 * layout2.getLeading() + 3 * layout2.getAscent()));
    		}
    	} else {
    		logger.debug("Attempting to access star=null!");
        }
    }


    protected class Animator implements ActionListener {
        private State state = State.FADE_IN;
        private int alphaIncrease;

        protected Animator(boolean start) {
            alphaIncrease = 255 * delay / fadeDelay;
            state = start ? State.FADE_IN : State.FADE_OUT;
        }


        State getState() {
            return state;
        }

        public void actionPerformed(ActionEvent e) {
            switch (state) {
                case FADE_IN:
                    alphaLevel += alphaIncrease;
                    if (alphaLevel > 255) {
                        alphaLevel = 255;
                        state = State.MAIN;
                    }
                    break;
                case FADE_OUT:
                    alphaLevel -= alphaIncrease;
                    if (alphaLevel < 0) {
                        alphaLevel = 0;
                        state = State.DONE;
                    }
                    break;
                case MAIN:
                    break;
                case DONE:
                    exit();
                    break;
            }
            animatedObject.next();
            repaint();
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE && interruptable) {
            interrupt();
        }
    }

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}

    public void mouseClicked(MouseEvent e) {}

    public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}
}

