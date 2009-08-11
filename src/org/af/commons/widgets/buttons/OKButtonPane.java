package org.af.commons.widgets.buttons;

import java.util.Arrays;

public class OKButtonPane extends HorizontalButtonPane{
    private static final long serialVersionUID = 1L;

    public OKButtonPane() {
        this("Ok");
    }

    public OKButtonPane(String caption1) {
        super(Arrays.asList(caption1), Arrays.asList(OK_CMD));
    }

}
