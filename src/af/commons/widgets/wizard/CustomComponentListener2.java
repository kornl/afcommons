package af.commons.widgets.wizard;

import af.commons.widgets.lists.SplitList;
import af.commons.widgets.lists.SplitListChangeListener;
import org.netbeans.spi.wizard.WizardPage;

import java.awt.*;

public class CustomComponentListener2 extends WizardPage.CustomComponentListener {
        public boolean accept(Component component) {
            return component instanceof SplitList;
        }

        public void startListeningTo(final Component component,
                                     final WizardPage.CustomComponentNotifier customComponentNotifier) {
            if (component instanceof SplitList) {
                ((SplitList) component).addSplitListChangeListener(
                        new SplitListChangeListener() {
                            public void modelStateChanged(java.util.List left, java.util.List right) {
                                customComponentNotifier.userInputReceived(component, right);
                    }
                });
            }
        }

    public void stopListeningTo(Component c) {}

    public Object valueFor(Component component) {
            if (component instanceof SplitList)
                return ((SplitList) component).getRight();
            return null;
        }
}
