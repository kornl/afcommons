package af.commons.widgets.validate;


public class IntegerTextField extends NumberTextField<Integer> {

    public IntegerTextField(String name, int cols, int min, int max) {
        super(name, "integer", cols, min, max);
    }

    public IntegerTextField(String name) {
        this(name, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public IntegerTextField(String name, int min, int max) {
        super(name, "integer", min, max);
    }

    protected Integer parseNumberString(String s) {
        return Integer.parseInt(s);        
    }
}
