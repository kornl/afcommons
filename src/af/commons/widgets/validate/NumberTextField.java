package af.commons.widgets.validate;


abstract public class NumberTextField<E extends Number> extends ValidatedTextField<E> {
    private static final long serialVersionUID = 1L;

    protected final String type;
    private E min, max;
    private boolean minInclusive = true;
    private boolean maxInclusive = true;

    public NumberTextField(String name, String type, int cols, E min, E max, boolean minInclusive, boolean maxInclusive) {
        super(name, cols);
        this.type = type;
        setRange(min, max, minInclusive, maxInclusive);
    }
  
    public NumberTextField(String name, String type, int cols, E min, E max) {
        this(name, type, cols, min, max, true, true);
    }


    public NumberTextField(String name, String type, E min, E max) {
        super(name);
        this.type = type;
        setRange(min, max);
    }

    public NumberTextField(String name, String type, E min, E max, boolean minInclusive, boolean maxInclusive) {
        this(name, type, min, max);
        setRange(min, max, minInclusive, maxInclusive);
    }

    protected abstract E parseNumberString(String s);

    public E getValidatedValue() throws ValidationException {
        E val;
        try {
            val = parseNumberString(getText());
        } catch (NumberFormatException e) {
            throw new ValidationException(getValidationErrorMsg());
        }
        if (val.doubleValue() < min.doubleValue() ||
                val.doubleValue() > max.doubleValue() ||
                (!minInclusive && val.doubleValue() == min.doubleValue()) ||
                (!maxInclusive && val.doubleValue() == max.doubleValue()))
            throw new ValidationException(getValidationErrorMsg());
        else
            return val;
    }

    public boolean isValidate() {
    	try {
    		getValidatedValue();
    	} catch (ValidationException v) {
    		return false;
    	}
    	return true;
    }
    
    public String getValidationErrorMsg() {
        return getDescriptiveName() + ": Only " + type + " values in " +
                (minInclusive ? "[" : "]") +
                min + ", " + ((max.doubleValue()==Double.MAX_VALUE)?"Inf":max) +
                (maxInclusive ? "]" : "[") +
                " are allowed";
    }


    public E getValue() {
        return parseNumberString(getText());
    }

    public void setRange(E min, E max) {
        setRange(min, max, true, true);
    }

    public void setRange(E min, E max, boolean minInclusive, boolean maxInclusive) {
        this.min = min;
        this.max = max;
        this.minInclusive = minInclusive;
        this.maxInclusive = maxInclusive;
        calcBgColor();
    }
}
