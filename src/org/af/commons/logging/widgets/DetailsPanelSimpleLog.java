package org.af.commons.logging.widgets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import org.af.commons.logging.ApplicationLog;

public class DetailsPanelSimpleLog extends DetailsPanel {
    public DetailsPanelSimpleLog(ApplicationLog appLog) {
    	setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;		
		c.gridx=0; c.gridy=0;
		c.gridwidth = 1; c.gridheight = 1;
		c.ipadx=10; c.ipady=10;
		c.weightx=1; c.weighty=1;

        add(new SimpleLogPanel(appLog), c);
    }
}
