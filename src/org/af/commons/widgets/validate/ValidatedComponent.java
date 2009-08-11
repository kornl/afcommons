package org.af.commons.widgets.validate;


public interface ValidatedComponent<E> {
    //public boolean hasValidValue();
    public E getValidatedValue() throws ValidationException;
    //public E getValidatedValueAndInform();
    public String getValidationErrorMsg();

    public String getDescriptiveName();
}

