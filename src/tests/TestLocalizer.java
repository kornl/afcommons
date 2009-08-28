package tests;

import org.af.commons.Localizer;
import org.af.commons.widgets.WidgetFactory;

import javax.swing.*;
import java.awt.*;

public class TestLocalizer extends JFrame{

    public TestLocalizer() throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JTabbedPane jTabbedPane = new JTabbedPane();
//        jTabbedPane.addTab("BorderLayout", new WithBorderLayout());
        jTabbedPane.addTab("BoxLayout", new WithBoxLayout());
        getContentPane().add(jTabbedPane);
        setSize(500,500);
        setVisible(true);
    }

    public static void main(String[] args) {
        Localizer loc = Localizer.getInstance();
        loc.setLanguage("en");
//        loc.addResourceBundle("MessageBundleDef");
        loc.addResourceBundle("org.af.commons.widgets.ResourceBundle");
        System.out.println(loc.getString("AFCOMMONS_BUTTONS_APPLY"));

        new TestLocalizer();

    }

    class WithBorderLayout extends JPanel {
        WithBorderLayout() {
            setLayout(new BorderLayout());
            add(new JButton("bla"), "Center");
            add(WidgetFactory.makeOkCancelButtonPanel(), "South");
        }
    }

    class WithBoxLayout extends JPanel{
        WithBoxLayout() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//            add(new JButton("bla"));
            Box bh = Box.createHorizontalBox();
            bh.add(new JButton("bla1"));
            bh.add(new JButton("bla2"));
            bh.setAlignmentX(Component.RIGHT_ALIGNMENT);
            add(bh);
            JComponent bp = WidgetFactory.makeOkCancelButtonPanel();
            bp.setAlignmentX(Component.RIGHT_ALIGNMENT);
            add(bp);
        }
    }
}
