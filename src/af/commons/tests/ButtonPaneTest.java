package af.commons.tests;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import af.commons.widgets.WidgetFactory;


public class ButtonPaneTest extends JFrame {

    class WithBorderLayout extends JPanel{
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


    public ButtonPaneTest() {
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
        new ButtonPaneTest();
    }
}
