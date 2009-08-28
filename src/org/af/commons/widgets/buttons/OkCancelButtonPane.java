package org.af.commons.widgets.buttons;

import org.af.commons.Localizer;

import java.util.Arrays;

/**
 * Horizontal button pane with 2 buttons: OK and Cancel.
 */
public class OkCancelButtonPane extends HorizontalButtonPane {

    /**
     * Constructor
     */
    public OkCancelButtonPane() {
        this(Localizer.getInstance().getString("AFCOMMONS_BUTTONS_OK"),
                Localizer.getInstance().getString("AFCOMMONS_BUTTONS_APPLY"));
    }

    /**
     * Constructor
     *
     * @param caption1 Caption for OK button
     * @param caption2 Caption for Apply button
     */
    public OkCancelButtonPane(String caption1, String caption2) {
        super(Arrays.asList(caption1, caption2), Arrays.asList(OK_CMD, CANCEL_CMD));
    }
}
