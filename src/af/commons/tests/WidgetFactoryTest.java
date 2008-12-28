package af.commons.tests;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import af.commons.widgets.WidgetFactory;


public class WidgetFactoryTest implements ActionListener {
    JFrame f1 = new JFrame();

    public WidgetFactoryTest() {
        Container cp = f1.getContentPane();
        cp.add(new JLabel("fghfg"));
        cp = WidgetFactory.makeDialogPanelWithButtons(cp, this);
        f1.setContentPane(cp);
        f1.setSize(300,300);
        f1.setVisible(true);
    }

    public static void main(String[] args) {
        WidgetFactoryTest wft = new WidgetFactoryTest();

    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == f1)
            System.out.println("f1");
    }
}
