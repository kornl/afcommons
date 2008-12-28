package af.commons.widgets.wizard;

import java.util.HashMap;

public class WizardModel {
    private HashMap<String, WizardPanelDescriptor> panels = new HashMap<String, WizardPanelDescriptor>();
    private String currentPanel;


    public void setCurrentPanel(String currentPanel) {
        this.currentPanel = currentPanel;
    }

    public void registerPanel(String id, WizardPanelDescriptor panel) {
        panels.put(id, panel);
    }

    WizardPanelDescriptor getCurrentPanelDescriptor() {
        return panels.get(currentPanel);
    }
}
