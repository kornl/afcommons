package org.af.commons.widgets.buttons;

import java.util.Arrays;

/**
 * 
 */
public class OkApplyCancelButtonPane extends HorizontalButtonPane {
    private static final long serialVersionUID = 1L;

    public OkApplyCancelButtonPane() {
        this("Ok", "Apply", "Cancel");
    }

    public OkApplyCancelButtonPane(String caption1, String caption2, String caption3) {
        super(Arrays.asList(caption1, caption2, caption3), Arrays.asList(OK_CMD, APPLY_CMD, CANCEL_CMD));
    }
}
