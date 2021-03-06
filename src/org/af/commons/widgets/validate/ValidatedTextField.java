package org.af.commons.widgets.validate;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

abstract public class ValidatedTextField<E> extends JTextField implements ValidatedComponent<E> {
    protected String descriptiveName;
    protected final Color normalCol;
    protected final Color errorCol = new Color(255, 100, 100);

    public ValidatedTextField(String descriptiveName) {
        this.descriptiveName = descriptiveName;
        normalCol = getBackground();
    }

    public ValidatedTextField(String descriptiveName, int cols) {
        this(descriptiveName);
        setColumns(cols);
    }

    public String getDescriptiveName() {
        return descriptiveName;
    }

    protected void calcBgColor() {
        setBackground(normalCol);
        try {
            if (isEnabled())
                getValidatedValue();
        } catch (ValidationException e) {
            setBackground(errorCol);
        }
        repaint();
    }


    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        calcBgColor();
    }


    public void setValue(E x){
        setText(x.toString());
    }

    public void setText(String t) {
        super.setText(t);
        calcBgColor();
    }

    protected void processKeyEvent(KeyEvent e) {
        super.processKeyEvent(e);
        calcBgColor();
    }

//    public void paint(Graphics g) {
//        setBackground(bgCol);
//        super.paint(g);
//    }
}
