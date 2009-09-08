package org.af.commons.widgets.buttons;

import java.util.Arrays;

import org.af.commons.Localizer;

/**
 * Horizontal button pane with 1 button: OK.
 */
public class OKButtonPane extends HorizontalButtonPane{
    private static final long serialVersionUID = 1L;

    /**
     * Constructor
     */
    public OKButtonPane() {
        this(Localizer.getInstance().getString("AFCOMMONS_WIDGETS_BUTTONS_OK"));
    }

    /**
     * Constructor
     *
     * @param caption1 Caption for OK button
     */
    public OKButtonPane(String caption1) {
        super(Arrays.asList(caption1), Arrays.asList(OK_CMD));
    }

    /**
     * Set the caption of the button.
     *
     * @param text Text for button.
     */
    public void setText(String text) {
        setText(0, text);
    }

}
