package org.af.commons.widgets;

import javax.swing.*;
import java.awt.*;

/**
 * Special subclass of JTextArea to display long labels across multiple lines with word wrapping.
 */
public class MultiLineLabel extends JTextArea{

    private Color textColor;
    private boolean antiAlias = false;

    /**
     * Constructor
     * @param text Displayed text
     */
    public MultiLineLabel(String text) {
        this();
        setText(text);
    }

    /**
     * Constructor
     */
    public MultiLineLabel() {
        setEditable(false);
        setEnabled(false);
        setWrapStyleWord(true);
        setLineWrap(true);
        setOpaque(false);
        setTextColor(Color.black);
    }

    /**
     * Returns color of displayed text. Default is Color.black.
     * @return Color of displayed text.
     */
    public Color getTextColor() {
        return textColor;
    }

    /**
     * Sets color of displayed text. Default is Color.black.
     * @param textColor Color of displayed text.
     */
    public void setTextColor(Color textColor) {
        this.textColor = textColor;
        setForeground(textColor);
        setDisabledTextColor(textColor);
    }

    /**
     * Set whether anti-aliasing should be enabled for the text.
     * @param antiAlias True if anti-aliasing should be enabled for the text.
     */
    public void setAntiAlias(boolean antiAlias) {
        this.antiAlias = antiAlias;
    }

    /**
     * Is anti-aliasing enabled for the text?
     * @return Is true if anti-aliasing is enabled for the text.
     */
    public boolean isAntiAlias() {
        return antiAlias;
    }


    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (antiAlias) {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        super.paint(g);
    }
}
