package org.af.commons.widgets.buttons;

import java.util.Arrays;

/**
 * 
 */
public class OkCancelButtonPane extends HorizontalButtonPane {


    public OkCancelButtonPane() {
        this("Ok", "Cancel");
    }

    public OkCancelButtonPane(String caption1, String caption2) {
        super(Arrays.asList(caption1, caption2), Arrays.asList(OK_CMD, CANCEL_CMD));
    }
}
