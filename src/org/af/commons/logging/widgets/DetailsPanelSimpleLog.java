package org.af.commons.logging.widgets;

import org.af.commons.logging.ApplicationLog;

public class DetailsPanelSimpleLog extends DetailsPanel{
    public DetailsPanelSimpleLog(ApplicationLog appLog) {
        add(new SimpleLogPanel(appLog));
    }
}
