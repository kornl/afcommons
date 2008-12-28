package af.commons.widgets.validate;

public class RealTextField extends NumberTextField<Double> {

    private static final long serialVersionUID = 1L;


    public RealTextField(String name, int cols, double min, double max, boolean minInclusive, boolean maxInclusive) {
        super(name, "real", cols, min, max, minInclusive, maxInclusive);
    }

    public RealTextField(String name, int cols, double min, double max) {
        super(name, "real", cols, min, max);
    }

    public RealTextField(String name, double min, double max, boolean minInclusive, boolean maxInclusive) {
        super(name, "real", min, max, minInclusive, maxInclusive);
    }

    public RealTextField(String name, double min, double max) {
        super(name, "real", min, max);
    }
    public RealTextField(String name) {
        this(name, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    protected Double parseNumberString(String s) {
        return Double.parseDouble(s);
    }
}
