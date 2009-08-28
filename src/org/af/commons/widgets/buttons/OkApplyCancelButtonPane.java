package org.af.commons.widgets.buttons;

import org.af.commons.Localizer;

import java.util.Arrays;

/**
 * Horizontal button pane with 3 buttons: OK, Apply and Cancel.
 */
public class OkApplyCancelButtonPane extends HorizontalButtonPane {

    /**
     * Constructor
     */
    public OkApplyCancelButtonPane() {
        this(Localizer.getInstance().getString("AFCOMMONS_WIDGETS_BUTTONS_OK"),
                Localizer.getInstance().getString("AFCOMMONS_BUTTONS_APPLY"),
                Localizer.getInstance().getString("AFCOMMONS_WIDGETS_BUTTONS_CANCEL"));
    }

    /**
     * Constructor
     *
     * @param caption1 Caption for OK button
     * @param caption2 Caption for Apply button
     * @param caption3 Caption for Cancel button
     */
    public OkApplyCancelButtonPane(String caption1, String caption2, String caption3) {
        super(Arrays.asList(caption1, caption2, caption3), Arrays.asList(OK_CMD, APPLY_CMD, CANCEL_CMD));
    }
}
