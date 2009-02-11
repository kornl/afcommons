package af.commons.widgets.wizard;

import af.commons.widgets.validate.IntegerTextField;
import af.commons.widgets.validate.RealTextField;
import org.netbeans.spi.wizard.WizardPage;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class WizardPage2 extends WizardPage {
    private String id;
    private DataManager dm;

    public WizardPage2(Map data, String id, String title) {
        super(id, title);
        this.dm = new DataManager(data);
        this.id = id;
    }

    public WizardPage2(String id, String title) {
        super(id, title);
        this.dm = new DataManager(new HashMap());
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public DataManager getDataManager() {
        return dm;
    }

    protected void nameComponent(Component comp, String name) {
       String s = getId() + "###" + name;
        comp.setName(s);    }

    protected void nameComponent(Component comp, String name, String group) {
        String s = getId() + "###" + name + "###" + group;
        comp.setName(s);
    }

    protected boolean isInGroup(Component comp, String group) {
        return comp.getName().endsWith("###" + group);
    }


    @Override
    protected CustomComponentListener createCustomComponentListener() {
        return new CustomComponentListener2();
    }

    @Override
    protected Object valueFrom(Component component) {
        if (component instanceof IntegerTextField)
            return ((IntegerTextField) component).getValue();
        if (component instanceof RealTextField)
            return ((RealTextField) component).getValue();
        return super.valueFrom(component);
    }

}
