package af.commons.widgets.wizard;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public abstract class WizardController implements ActionListener {
    protected Wizard wizard;

    protected abstract String getFirstPanel();


    public void actionPerformed(ActionEvent e) {
        WizardPanelDescriptor current = wizard.getCurrentPanelDescriptor();

        if(e.getActionCommand().equals("Finish")) {
            String msg = current.canGoNext();
            if (msg == null) {
                current.aboutToHidePanel();
                current.goNext();
                wizard.finish();
            }
            else
                JOptionPane.showMessageDialog(current.getPanelComponent(), msg);
        }
        else if(e.getActionCommand().equals("Next")) {
            String msg = current.canGoNext();
            if (msg == null) {
                current.goNext();
                wizard.setCurrentPanel(current.getNextPanel());
                wizard.getCurrentPanelDescriptor().setPreviousPanel(current.getPanelDescriptorIdentifier());
                wizard.getCurrentPanelDescriptor().enterPanel();
            }
            else
                JOptionPane.showMessageDialog(current.getPanelComponent(), msg);
        }

        else if(e.getActionCommand().equals("Back")) {
            wizard.setCurrentPanel(current.getPreviousPanel());
            wizard.getCurrentPanelDescriptor().setNextPanel(current.getPanelDescriptorIdentifier());
        }
    }


    public void setWizard(Wizard wizard) {
        this.wizard = wizard;
    }
}
