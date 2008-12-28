package af.commons.widgets.validate;

import java.awt.Color;
import java.awt.event.KeyEvent;

import javax.swing.JTextField;

abstract public class ValidatedTextField<E> extends JTextField implements ValidatedComponent<E> {
    private String name;
    private final Color normalCol;
    private final Color errorCol = new Color(255, 100, 100);

    public ValidatedTextField(String name) {
        this.name = name;
        normalCol = getBackground();
    }

    public ValidatedTextField(String name, int cols) {
        this(name);
        setColumns(cols);
    }

    public String getName() {
        return name;
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
