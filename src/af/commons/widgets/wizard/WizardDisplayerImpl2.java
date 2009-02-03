package af.commons.widgets.wizard;

import org.netbeans.api.wizard.displayer.WizardDisplayerImpl;
import org.netbeans.spi.wizard.Wizard;

import java.awt.*;

public class WizardDisplayerImpl2 extends WizardDisplayerImpl {
    public static Object showWizard(Wizard wizard, int width, int height) {
        Rectangle bounds = new Rectangle(width, height);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - width) / 2;
        int y = (d.height - height) / 2;
        bounds.setLocation(x,y);
        return WizardDisplayerImpl.showWizard(wizard, bounds);
    }
}
