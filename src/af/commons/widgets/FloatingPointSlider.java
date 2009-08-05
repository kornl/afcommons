package af.commons.widgets;

import java.util.Hashtable;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;

/**
 * Extends the JSlider to Double values and logarithmic and logistic scales.
 */
public class FloatingPointSlider extends JSlider {

	protected int scale = 0;
	protected Double min = 0.0;
	protected Double max = 1.0;
	protected int ticks = 10;
	
	public static final int IDENTITY = 0;
	public static final int LOGARITHMIC = 1;
	public static final int LOGISTIC = 2;
	
	protected static final int RESOLUTION = 100;
	
	protected int labelLength = 6;
	
	public int getLabelLength() {
		return labelLength;
	}

	public void setLabelLength(int labelLength) {
		this.labelLength = labelLength;
	}

	public FloatingPointSlider(Double min, Double max, int ticks)  {
		this(min, max, ticks, IDENTITY);
	}
	
	protected static double logistic(double x) {
		return Math.log(x/(1-x));
	}
	
	protected static double logisticI(double x) {
		return 1/(1+Math.exp(-x));
	}
	
	public FloatingPointSlider(Double min, Double max, int ticks, int scale)  {
		super(0,ticks*RESOLUTION);
		this.min = min;
		this.max = max;
		this.ticks = ticks;
		this.scale = scale;
		/* Check scales */
		if (max <= min) throw new IllegalArgumentException("max has to be greater than min.");		
		if (scale == LOGARITHMIC && min <= 0) throw new IllegalArgumentException("min has to be greater than 0 on the logarithmic scale.");		
		if ((scale == LOGISTIC) && (min <= 0 || max >= 1)) throw new IllegalArgumentException("min and max has to be between 0 and 1 on the logistic scale.");
		/* Building labels */
		Hashtable<Integer, JComponent> dict = new Hashtable<Integer, JComponent>(); 
		for (int i = 0; i<(ticks+1)*RESOLUTION; i++) {
			if (i%100==0) {				
				Double d = convert(i);			
				d = ((double)Math.round(d*Math.pow(10,labelLength-2)))/Math.pow(10,labelLength-2);
				String label = d.toString();
				if (d.toString().endsWith(".0")) {
						label = label.substring(0, label.length()-2);
					} else {
						label = label.substring(0, Math.min(labelLength, label.length()));
				}				
				dict.put(i, new JLabel(label));
			} else {
				dict.put(i, new JLabel(""));
			}
		}

		setValue(ticks*RESOLUTION/2);
		setMajorTickSpacing(RESOLUTION);
		setLabelTable(dict);
		setPaintTicks(true);
    	setPaintLabels(true);
	}
	
	public double convert(int x) {
		Double d = (max-min)/(ticks*RESOLUTION)*x+min;
		if (scale == LOGARITHMIC) {
			//d = Math.pow(10, (Math.log10(max)-Math.log10(min))/(ticks*RESOLUTION)*x+Math.log10(min));
			d = Math.exp((Math.log(max)-Math.log(min))/(ticks*RESOLUTION)*x+Math.log(min));
		} else if (scale == LOGISTIC) {
			d = logisticI((logistic(max)-logistic(min))/(ticks*RESOLUTION)*x+logistic(min));
		}
		return d;
	}
	
	public double getDoubleValue() {
		return convert(getValue());
	}
	
	/**
	 * Test for this class.
	 * @param args will be ignored
	 */
	public static void main(String args[]) {
		JFrame frame = new JFrame();
		//frame.getContentPane().add(new FloatingPointSlider(0.001,0.009,10));
		//frame.getContentPane().add(new FloatingPointSlider(0.001,1000.0,6, LOGARITHMIC));
		frame.getContentPane().add(new FloatingPointSlider(0.001,1000000000.0,6, LOGARITHMIC));
		//frame.getContentPane().add(new FloatingPointSlider(0.001,0.999,12, LOGISTIC));
		frame.pack();
		frame.setVisible(true);
	}

}
