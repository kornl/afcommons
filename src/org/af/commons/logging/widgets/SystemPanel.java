package org.af.commons.logging.widgets;

import org.af.commons.logging.ApplicationLog;

public class SystemPanel extends SimplePanel {
    private static final long serialVersionUID = 1L;

    public SystemPanel(ApplicationLog appLog) {
        super(appLog);
    }

    protected void makeComponents() {
        super.makeComponents();
    }

    protected String getText() {    	
		return getAppLog().getSystemInfo();
    }
}
