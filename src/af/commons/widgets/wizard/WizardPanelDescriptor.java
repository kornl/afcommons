package af.commons.widgets.wizard;

import java.awt.Component;

public abstract class WizardPanelDescriptor {

    private Wizard wizard;
    protected Component targetPanel;
    private String panelIdentifier;
    private String title;
    private boolean isBackEnabled = true;
    private boolean isNextEnabled = true;
    private boolean isLastPanel = false;
    private String previousPanel;
    private String nextPanel;

    public WizardPanelDescriptor(Wizard wizard, String title) {
        this.wizard = wizard;
        this.title = title;
    }

    public final Component getPanelComponent() {
        return targetPanel;
    }

    public final void setPanelComponent(Component panel) {
        targetPanel = panel;
    }


    public boolean isLastPanel() {
        return isLastPanel;
    }

    public void setLastPanel(boolean lastPanel) {
        isLastPanel = lastPanel;
    }

    public void setBackEnabled(boolean backEnabled) {
        isBackEnabled = backEnabled;
    }

    public void setNextEnabled(boolean nextEnabled) {
        isNextEnabled = nextEnabled;
    }

    public boolean isBackEnabled() {
        return isBackEnabled;
    }

    public boolean isNextEnabled() {
        return isNextEnabled;
    }

    public final String getPanelDescriptorIdentifier() {
        return panelIdentifier;
    }

    public final void setPanelDescriptorIdentifier(String id) {
        panelIdentifier = id;
    }

    public String getTitle() {
        return title;
    }


    public String getNextPanel() {
        return nextPanel;
    }

    public void setNextPanel(String nextPanel) {
        this.nextPanel = nextPanel;
    }


    public String getPreviousPanel() {
        return previousPanel;
    }

    public void setPreviousPanel(String previousPanel) {
        this.previousPanel = previousPanel;
    }

    public void aboutToDisplayPanel() {}

    public void displayingPanel() {}

    public void aboutToHidePanel() {}

    public abstract String canGoNext();

    public void goNext() {}

    public void enterPanel() {}

    protected Wizard getWizard() {
        return wizard;
    }
}
