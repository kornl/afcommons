package org.af.commons.widgets.validate;

import org.af.commons.Localizer;

public class RealTextField extends NumberTextField<Double> {

    private static final long serialVersionUID = 1L;


    public RealTextField(String name, int cols, double min, double max, boolean minInclusive, boolean maxInclusive) {
        super(name, Localizer.getInstance().getString("AFCOMMONS_WIDGETS_VALIDATE_REAL"),
                cols, min, max, minInclusive, maxInclusive);
    }

    public RealTextField(String name, int cols, double min, double max) {
        super(name, Localizer.getInstance().getString("AFCOMMONS_WIDGETS_VALIDATE_REAL"),
                cols, min, max);
    }

    public RealTextField(String name, double min, double max, boolean minInclusive, boolean maxInclusive) {
        super(name, Localizer.getInstance().getString("AFCOMMONS_WIDGETS_VALIDATE_REAL"),
                min, max, minInclusive, maxInclusive);
    }

    public RealTextField(String name, double min, double max) {
        super(name, Localizer.getInstance().getString("AFCOMMONS_WIDGETS_VALIDATE_REAL"),
                min, max);
    }
    public RealTextField(String name) {
        this(name, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    protected Double parseNumberString(String s) {
        return Double.parseDouble(s);
    }
}
