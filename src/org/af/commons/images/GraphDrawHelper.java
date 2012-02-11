package org.af.commons.images;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Arc2D.Double;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;

/**
 * 
 * Die G9-Gui-Klasse stellt statische Methoden bereit, die auf
 * java.awt.Graphics-Objekten arbeiten.
 * 
 * @version 26 Dec 2001
 * @author G9-Gui
 * @see java.awt.Graphics
 */

public class GraphDrawHelper {

	public static int corr = -1;

	/**
	 * Zeichnet einen Pfeil mit rechtwinkliger Spitze
	 * 
	 * @param g
	 *            das zu bearbeitende Graphics-Objekt
	 * @param x1
	 *            die Abszisse des Pfeilanfangs
	 * @param y1
	 *            die Ordinate des Pfeilanfangs
	 * @param x2
	 *            die Abszisse des Pfeilendes
	 * @param y2
	 *            die Ordinate des Pfeilendes
	 * @param l
	 *            die Länge der Pfeilspitze
	 */
	public static void malPfeil(Graphics g, int x1, int y1, int x2, int y2,
			int l) {
		g.drawLine(x1, y1, x2, y2);
		int dx = (x2 - x1);
		int dy = (y2 - y1);
		double q;
		if (dx == 0) {
			q = ((double) dy) / (double) .1d;
		} else {
			q = ((double) dy) / (double) dx;
		}
		double w = Math.atan(q);
		w = w + (Math.PI / 4);
		if ((dx >= 0 && dy < 0) || (dx < 0 && dy > 0))
			corr = -1;
		else
			corr = 1;
		int px = (int) (x2 - Math.cos(w) * l * sign(dx));
		int py = (int) (y2 - Math.sin(w) * l * sign(dy) * corr);
		g.drawLine(x2, y2, px, py);
		w = Math.atan(q);
		w = w - (Math.PI / 4);
		px = (int) (x2 - Math.cos(w) * l * sign(dx));
		py = (int) (y2 - Math.sin(w) * l * sign(dy) * corr);
		g.drawLine(x2, y2, px, py);
	}

	/**
	 * Zeichnet einen Pfeil mit ausgefüllter rechtwinkliger Spitze
	 * 
	 * @param g
	 *            das zu bearbeitende Graphics-Objekt
	 * @param x1
	 *            die Abszisse des Pfeilanfangs
	 * @param y1
	 *            die Ordinate des Pfeilanfangs
	 * @param x2
	 *            die Abszisse des Pfeilendes
	 * @param y2
	 *            die Ordinate des Pfeilendes
	 * @param l
	 *            die Länge der Pfeilspitze
	 */

	public static void malVollenPfeil(Graphics g, int x1, int y1, int x2,
			int y2, int l, boolean curve) {
		
		Graphics2D g2d = (Graphics2D) g;

		int[] c = GraphDrawHelper.getControlPoints(x1, y1, x2, y2);
		
	    QuadCurve2D quadcurve = new QuadCurve2D.Float(x1, y1, c[0], c[1] ,x2, y2);
	    
	    if (curve) {	    
	    	g2d.draw(quadcurve);
	    	x1 = c[0];
		    y1 = c[1];		    
	    } else {
	    	g.drawLine(x1, y1, x2, y2);
	    }	    
	    
		int dx = (x2 - x1);
		int dy = (y2 - y1);
		double q;
		if (dx == 0) {
			q = ((double) dy) / (double) .1d;
		} else {
			q = ((double) dy) / (double) dx;
		}
		double w = Math.atan(q);
		w = w + (Math.PI / 4);
		if ((dx >= 0 && dy < 0) || (dx < 0 && dy > 0))
			corr = -1;
		else
			corr = 1;
		int px = (int) (x2 - Math.cos(w) * l * sign(dx));
		int py = (int) (y2 - Math.sin(w) * l * sign(dy) * corr);
		w = Math.atan(q);
		w = w - (Math.PI / 4);
		int px2 = (int) (x2 - Math.cos(w) * l * sign(dx));
		int py2 = (int) (y2 - Math.sin(w) * l * sign(dy) * corr);
		g.fillPolygon(new int[] { x2, px, px2 }, new int[] { y2, py, py2 }, 3);
	}

	public static int sign(int zahl) {
		if (zahl > 0)
			return 1;
		else
			return -1;

	}

	/**
	 * Zeichnet einen Pfeil
	 * 
	 * @param g
	 *            das zu bearbeitende Graphics-Objekt
	 * @param x1
	 *            die Abszisse des Pfeilanfangs
	 * @param y1
	 *            die Ordinate des Pfeilanfangs
	 * @param x2
	 *            die Abszisse des Pfeilendes
	 * @param y2
	 *            die Ordinate des Pfeilendes
	 * @param l
	 *            die Länge der Pfeilspitze
	 * @param grad
	 *            der Winkel zwischen Pfeilspitzenschenkeln und Pfeilrumpf
	 */

	public static void malPfeil(Graphics g, int x1, int y1, int x2, int y2,
			int l, int grad) {
		g.drawLine(x1, y1, x2, y2);
		int dx = (x2 - x1);
		int dy = (y2 - y1);
		double q;
		if (dx == 0) {
			q = ((double) dy) / (double) .1d;
		} else {
			q = ((double) dy) / (double) dx;
		}
		double w = Math.atan(q);
		w = w + (2 * Math.PI * grad / 360);
		if ((dx >= 0 && dy < 0) || (dx < 0 && dy > 0))
			corr = -1;
		else
			corr = 1;
		int px = (int) (x2 - Math.cos(w) * l * sign(dx));
		int py = (int) (y2 - Math.sin(w) * l * sign(dy) * corr);
		g.drawLine(x2, y2, px, py);
		w = Math.atan(q);
		w = w - (2 * Math.PI * grad / 360);
		px = (int) (x2 - Math.cos(w) * l * sign(dx));
		py = (int) (y2 - Math.sin(w) * l * sign(dy) * corr);
		g.drawLine(x2, y2, px, py);
	}

	/**
	 * Zeichnet einen Pfeil mit ausgefüllter Spitze
	 * 
	 * @param g
	 *            das zu bearbeitende Graphics-Objekt
	 * @param x1
	 *            die Abszisse des Pfeilanfangs
	 * @param y1
	 *            die Ordinate des Pfeilanfangs
	 * @param x2
	 *            die Abszisse des Pfeilendes
	 * @param y2
	 *            die Ordinate des Pfeilendes
	 * @param l
	 *            die Länge der Pfeilspitze
	 * @param grad
	 *            der Winkel zwischen Pfeilspitzenschenkeln und Pfeilrumpf
	 */

	public static void malVollenPfeil(Graphics g, int x1, int y1, int x2,
			int y2, int l, int grad) {
		g.drawLine(x1, y1, x2, y2);
		int dx = (x2 - x1);
		int dy = (y2 - y1);
		double q;
		if (dx == 0) {
			q = ((double) dy) / (double) .1d;
		} else {
			q = ((double) dy) / (double) dx;
		}
		double w = Math.atan(q);
		w = w + (2 * Math.PI * grad / 360);
		if ((dx >= 0 && dy < 0) || (dx < 0 && dy > 0))
			corr = -1;
		else
			corr = 1;
		int px = (int) (x2 - Math.cos(w) * l * sign(dx));
		int py = (int) (y2 - Math.sin(w) * l * sign(dy) * corr);
		w = Math.atan(q);
		w = w - (2 * Math.PI * grad / 360);
		int px2 = (int) (x2 - Math.cos(w) * l * sign(dx));
		int py2 = (int) (y2 - Math.sin(w) * l * sign(dy) * corr);
		g.fillPolygon(new int[] { x2, px, px2 }, new int[] { y2, py, py2 }, 3);
	}

	// The "Smoothness" of the Curve which is made of Lines
	final static int MAX_LINES = 25;

	static Dimension preferredSize = new Dimension(300, 100);

	// The 4 Control-Points and the Lines Points
	static double ctrl_pts[][] = { { 0d, 0d }, { 0d, 100d }, { 100d, 0d },
			{ 100d, 100d } };
	static double line_pts[][] = new double[MAX_LINES][2];

	static public void drawSpline(Graphics g, int x1, int y1, int x2, int y2) {
		// Sets the Control-Points for this Bezier Spline.
		if (x1 <= x2) { // Steps in Depency Order
			ctrl_pts[0][0] = (double) x1;
			ctrl_pts[0][1] = (double) y1;
			ctrl_pts[1][0] = (double) x2;
			ctrl_pts[1][1] = (double) y1;
			ctrl_pts[2][0] = (double) x1;
			ctrl_pts[2][1] = (double) y2;
			ctrl_pts[3][0] = (double) x2;
			ctrl_pts[3][1] = (double) y2;
			// calc_line_pts();
		} else { // Steps NOT in Depency Order (Broken Depency!!!)
			ctrl_pts[0][0] = (double) x1;
			ctrl_pts[0][1] = (double) y1;
			ctrl_pts[1][0] = 50d + (double) x1;
			ctrl_pts[1][1] = 50d + (double) y1;
			ctrl_pts[2][0] = -50d + (double) x2;
			ctrl_pts[2][1] = -50d + (double) y2;
			ctrl_pts[3][0] = (double) x2;
			ctrl_pts[3][1] = (double) y2;
		}
		// calc_line_pts();
		// Punkte berechnen
		for (int i = 0; i < MAX_LINES; i++) {
			double a, b, c, d;
			double t = (1d / (double) MAX_LINES) * (double) i;
			a = ctrl_pts[0][0];
			b = ctrl_pts[1][0];
			c = ctrl_pts[2][0];
			d = ctrl_pts[3][0];
			line_pts[i][0] = a - 3d * a * t + 3d * t * b + 3d * a * t * t - 6d
					* t * t * b + 3d * t * t * c - a * t * t * t + 3d * t * t
					* t * b - 3d * t * t * t * c + t * t * t * d;
			a = ctrl_pts[0][1];
			b = ctrl_pts[1][1];
			c = ctrl_pts[2][1];
			d = ctrl_pts[3][1];
			line_pts[i][1] = a - 3d * a * t + 3d * t * b + 3d * a * t * t - 6d
					* t * t * b + 3d * t * t * c - a * t * t * t + 3d * t * t
					* t * b - 3d * t * t * t * c + t * t * t * d;
		}
		// That's the Painting ;)
		for (int i = 1; i < MAX_LINES; i++)
			g.drawLine((int) line_pts[i - 1][0], (int) line_pts[i - 1][1],
					(int) line_pts[i][0], (int) line_pts[i][1]);
		malVollenPfeil(g, (int) line_pts[MAX_LINES - 1][0],
				(int) line_pts[MAX_LINES - 1][1], x2, y2, 6, false);
	}
	
	/**
	 * Returns the point in the middle of the curve from (x1,x2) to (x2,y2). 
	 * @param x1 die Abszisse des Pfeilanfangs
	 * @param y1 die Ordinate des Pfeilanfangs
	 * @param x2 die Abszisse des Pfeilendes
	 * @param y2 die Ordinate des Pfeilendes
	 * @return point in the middle of the curve from (x1,x2) to (x2,y2)
	 */
	static public int[] getDrawPoints(long x1, long y1, long x2, long y2) {
		int[] c = GraphDrawHelper.getControlPoints(x1, y1, x2, y2);		
		return new int[] {(int)(0.25*x1+0.25*x2+0.5*c[0]),(int)(0.25*y1+0.25*y2+0.5*c[1])};
	}
	
	static protected Rectangle2D getBounds(long x1, long y1, long x2, long y2) {
		int[] c = GraphDrawHelper.getControlPoints(x1, y1, x2, y2);
		QuadCurve2D quadcurve = new QuadCurve2D.Float(x1, y1, c[0], c[1] ,x2, y2);
		return quadcurve.getBounds2D();
	}
	
	static protected int[] getControlPoints(long x1, long y1, long x2, long y2) {
		double a = x2-x1;
		double b = y2-y1;
		
		double d1 = 100*Math.signum(y2-y1+0.1);
		double d2 = -a/(b+0.1)*d1;
		
		double dd = Math.sqrt(a*a+b*b);
		double s2 = dd/(Math.sqrt(d1*d1+d2*d2)*2);
		
		int c1 = (int)((x1+x2)/2+d1*s2);
		int c2 = (int)((y1+y2)/2+d2*s2);
		
		return new int[] {c1,c2};
	}

	/*
	 * Given three points (a1, a2), (b1, b2), (c1, c2) this function returns
	 * the center of the well-defined circle that goes through all of the three points.  
	 */
	public static double[] getCenter(double a1, double a2, double b1, double b2, double c1, double c2) throws GraphException {
		return getCenter(a1, a2, b1, b2, c1, c2, 0.05);
	}
 
	
	/*
	 * Given three points (a1, a2), (b1, b2), (c1, c2) this function returns
	 * the center of the well-defined circle that goes through all of the three points.  
	 */
	public static double[] getCenter(double a1, double a2, double b1, double b2, double c1, double c2, double eps) throws GraphException {
		double x1, x2;
		if ((b2-c2)==0) {
			x2=1; x1=0;
		} else {
			x2 = - (b1-c1)/(b2-c2);
			x1 = 1;
		}	
		double z1, z2;
		if ((a2-b2)==0) {
			z2=1; z1=0;
		} else {
			z2 = - (a1-b1)/(a2-b2);
			z1 = 1;
		}		
		if (Math.abs((b1-a1)/(b2-a2)-(c1-b1)/(c2-b2))<eps && Math.signum(b1-a1)==Math.signum(c1-b1)) {
			throw new GraphException("Slopes are too similar.");
		}
		double c, d;
		if (z1!=0 && x1==0) {			
			c = (c1-a1)/(2*z1);
			double m1 = (a1+b1)/2+c*z1;
			double m2 = (a2+b2)/2+c*z2;
			return new double[] {m1, m2};
		} else if (x1!=0 && z1==0) {
			d = (a1-c1)/(2*x1);
			double m1 = (b1+c1)/2+d*x1;
			double m2 = (b2+c2)/2+d*x2;
			return new double[] {m1, m2};
		} else if ((x1==0 && z1==0)||(x2==0 && z2==0)) {
			throw new GraphException("Slopes are too similar.");
		} else if (z2!=0 && x2==0) {			
			c = (c2-a2)/(2*z2);
			double m1 = (a1+b1)/2+c*z1;
			double m2 = (a2+b2)/2+c*z2;
			return new double[] {m1, m2};
		} else if (x2!=0 && z2==0) {			
			d = (a2-c2)/(2*x2);
			double m1 = (b1+c1)/2+d*x1;
			double m2 = (b2+c2)/2+d*x2;
			return new double[] {m1, m2};
		} else {
			if ((x2-x1*z2/z1)==0) {
				if ((z2-z1*x2/x1)==0) throw new GraphException("Can this happen?");
				c = ((c2-a2)/2+((a1-c1)/2*x1)*x2)/(z2-z1*x2/x1);
				double m1 = (a1+b1)/2+c*z1;
				double m2 = (a2+b2)/2+c*z2;
				return new double[] {m1, m2};				
			}
			d = ((a2-c2)/2+((c1-a1)/2*z1)*z2)/(x2-x1*z2/z1);		
		}

		double m1 = (b1+c1)/2+d*x1;
		double m2 = (b2+c2)/2+d*x2;
		return new double[] {m1, m2};
	}
	
	public static double[] getAngle(double a1, double a2, double b1, double b2, double c1, double c2, double m1, double m2) {
		double phi1;
		double phi2;
		double phi3;
		if ((a1-m1)==0) {
			phi1 = 90 + ((m2-a2>0)?0:180);
		} else {
			phi1 = Math.atan((-a2+m2)/(a1-m1))*360/(2*Math.PI)+((a1-m1<0)?180:0);
		}
		if ((c1-m1)==0) {
			phi2 = 90 + ((m2-c2>0)?0:180);
		} else {
			phi2 = Math.atan((-c2+m2)/(c1-m1))*360/(2*Math.PI)+((c1-m1<0)?180:0);
		}
		if ((b1-m1)==0) {
			phi3 = 90 + ((m2-b2>0)?0:180);
		} else {
			phi3 = Math.atan((-b2+m2)/(b1-m1))*360/(2*Math.PI)+((b1-m1<0)?180:0);
		}		
		phi1 = (phi1 + 360) % 360;
		phi2 = (phi2 + 360) % 360;
		phi3 = (phi3 + 360) % 360;
		if (phi2 > phi1) {
			if (phi2 > phi3 && phi3 > phi1) {		
				return new double[] {phi1, phi2-phi1, phi1, phi2, phi3};			
			} else {
				return new double[] {phi2, (phi1-phi2+360) % 360, phi1, phi2, phi3};			
			}
		}
		if (phi1 > phi3 && phi3 > phi2) {
			return new double[] {phi1, phi2-phi1, phi1, phi2, phi3};
		} else {
			return new double[] {phi1, (phi2-phi1+360) % 360, phi1, phi2, phi3};
		}
	}
	
	/**
	 * 
	 * @param g das zu bearbeitende Graphics-Objekt
	 * @param a1 die Abszisse des Pfeilanfangs
	 * @param a2 die Ordinate des Pfeilanfangs
	 * @param b1 die Abszisse des Hilfspunktes
	 * @param b2 die Ordinate des Hilfspunktes
	 * @param c1 die Abszisse des Pfeilendes
	 * @param c2 die Ordinate des Pfeilendes
	 * @param l die Länge der Pfeilspitze
	 * @param grad der Winkel zwischen Pfeilspitzenschenkeln und Pfeilrumpf (0-360)
	 */
	public static void drawEdge(Graphics g, double a1, double a2, double b1, double b2, double c1, double c2, int l, int grad, boolean fill) {
		try {
			double[] m = getCenter(a1, a2, b1, b2, c1, c2, 0.001);
			double r = Math.sqrt((m[0]-a1)*(m[0]-a1)+(m[1]-a2)*(m[1]-a2));
			double d = Math.sqrt((c1-a1)*(c1-a1)+(c2-a2)*(c2-a2));
			//if (2*Math.PI*r/360>6*d/200) throw new GraphException("Edge is too linear.");			
			double[] phi = getAngle(a1, a2, b1, b2, c1, c2, m[0], m[1]);
			try {
				Double arc = new Arc2D.Double(m[0]-r, m[1]-r, 2*r, 2*r, phi[0], phi[1], Arc2D.OPEN);
				Graphics2D g2d = (Graphics2D)g;
				g2d.draw(arc);
			} catch (Exception e) {
				g.drawArc((int)(m[0]-r), (int)(m[1]-r), (int)(2*r), (int)(2*r), (int)(phi[0]), (int)(phi[1]));
			}			
			drawArrowHead(g, c1, c2, (phi[0]==phi[2]&&phi[1]>0)||(phi[0]==phi[1]&&phi[1]<0)?phi[3]+90:(phi[3]+90+180)%360, l, grad, fill);			
		} catch (GraphException e) {
			GraphDrawHelper.malVollenPfeil(g, (int)a1, (int)a2, (int)c1, (int)c2, l, grad);			
		}
	}

	public static void drawArrowHead(Graphics g, double c1, double c2, double phi, int l, int grad, boolean fill) {	
		phi = (phi + 180) % 360;
		int px = (int) (c1 + Math.cos((2 * Math.PI * (phi+grad) / 360)) * l );
		int py = (int) (c2 - Math.sin((2 * Math.PI * (phi+grad) / 360)) * l );				
		int px2 = (int) (c1 + Math.cos((2 * Math.PI * (phi-grad) / 360)) * l );
		int py2 = (int) (c2 - Math.sin((2 * Math.PI * (phi-grad) / 360)) * l );
		if (fill) {
			g.fillPolygon(new int[] { (int)c1, px, px2 }, new int[] { (int)c2, py, py2 }, 3);
		} else {
			g.drawLine((int)c1, (int)c2, px, py);
			g.drawLine((int)c1, (int)c2, px2, py2);
		}
		
	}
}
