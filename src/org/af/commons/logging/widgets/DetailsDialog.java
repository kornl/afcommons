package org.af.commons.logging.widgets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

public class DetailsDialog extends JFrame {
    private DetailsPanel detailsPanel;


    public DetailsDialog(DetailsPanel detailsPanel) {
        this.detailsPanel = detailsPanel;
        setTitle("Details");
        doTheLayout();
        pack();
        int w = getWidth();
        int h = getHeight();
        if (w > 800)
            w = 800;
        if (h < 600)
            h = 600;
        setSize(w,h);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void doTheLayout() {
    	getContentPane().setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.BOTH;		
		c.gridx=0; c.gridy=0;
		c.gridwidth = 1; c.gridheight = 1;
		c.ipadx=10; c.ipady=10;
		c.weightx=1; c.weighty=1;

        getContentPane().add(detailsPanel , c);
    }
}
