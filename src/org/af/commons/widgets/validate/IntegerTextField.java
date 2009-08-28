package org.af.commons.widgets.validate;

import org.af.commons.Localizer;


public class IntegerTextField extends NumberTextField<Integer> {

    public IntegerTextField(String name, int cols, int min, int max) {
        super(name, Localizer.getInstance().getString("AFCOMMONS_WIDGETS_VALIDATE_INTEGER"), cols, min, max);
    }

    public IntegerTextField(String name) {
        this(name, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public IntegerTextField(String name, int min, int max) {
        super(name, Localizer.getInstance().getString("AFCOMMONS_WIDGETS_VALIDATE_INTEGER"), min, max);
    }

    protected Integer parseNumberString(String s) {
        return Integer.parseInt(s);        
    }
}
