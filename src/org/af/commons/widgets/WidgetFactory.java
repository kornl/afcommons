package org.af.commons.widgets;


import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;

import org.af.commons.widgets.buttons.HorizontalButtonPane;
import org.af.commons.widgets.buttons.OkCancelButtonPane;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * 
 */
public class WidgetFactory {
    public static OkCancelButtonPane makeOkCancelButtonPanel() {
        return new OkCancelButtonPane();
    }

    public static EmptyBorder makeEmptyBorder() {
        return makeEmptyBorder(5);
    }

    public static EmptyBorder makeEmptyBorder(int w) {
        return new EmptyBorder(w, w, w, w);
    }

//    public static Box surroundBoxWithGlue(Box b) {
//
//    }

    public static Box surroundComponentWithGlue(Component c) {
        Box vBox = Box.createVerticalBox();
        vBox.add(Box.createVerticalGlue());
        vBox.add(c);
        vBox.add(Box.createVerticalGlue());
        Box hBox = Box.createHorizontalBox();
        hBox.add(Box.createHorizontalGlue());
        hBox.add(vBox);
        hBox.add(Box.createHorizontalGlue());
        return hBox;
    }

//    private final class Enabler implements ActionListener {
//        private JCheckBox chb;
//        private final Component c;
//
//        public Enabler(JCheckBox chb, Component c) {
//            this.chb = chb;
//            this.c = c;
//            chb.addActionListener(this);
//            c.setEnabled(chb.isSelected());
//        }
//
//        public void actionPerformed(ActionEvent e) {
//            c.setEnabled(chb.isSelected());
//        }
//    }

    public static void registerEnabler(final JCheckBox chb, final Component c) {

        chb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.setEnabled(chb.isSelected());
            }
        });

        c.setEnabled(chb.isSelected());
    }

    public static void registerDisabler(final JCheckBox chb, final Component c) {

        chb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                c.setEnabled(!chb.isSelected());
            }
        });

        c.setEnabled(!chb.isSelected());
    }

    public static void setFontSizeGlobal(int size) {
        for (Enumeration<?> e = UIManager.getDefaults().keys(); e.hasMoreElements();) {
            Object key = e.nextElement();
            Object value = UIManager.get(key);

            if (value instanceof Font) {
                Font f = (Font) value;

                UIManager.put(key, new FontUIResource(f.getName(), f.getStyle(), size));
            }
        }
    }

    public static void showCompleteTitle(JDialog dialog) {
        String t = dialog.getTitle();
        // this is a hack, but we need to take into account
        // icons before and after title
        t += "XXXXXXXX";
        Font f = UIManager.getFont("InternalFrame.titleFont");

        int w = dialog.getSize().width;
        int h = dialog.getSize().height;

        int w2 = dialog.getFontMetrics(f).stringWidth(t);

        if (w2 > w)
            dialog.setSize(w2, h);

    }

    public static JPanel makeDialogPanelWithButtons (Container content, HorizontalButtonPane hbp, ActionListener al) {
        hbp.addActionListener(al);
        JPanel p = new JPanel();
        String cols = "fill:pref:grow";
        String rows = "fill:pref:grow, 5dlu, bottom:pref:n";
        FormLayout layout = new FormLayout(cols, rows);
        p.setLayout(layout);

        CellConstraints cc = new CellConstraints();
        p.add(content, cc.xy(1,1));
        p.add(hbp, cc.xy(1,3, "right, bottom"));
        p.setBorder(makeEmptyBorder());
        return p;
    }

    public static JPanel makeDialogPanelWithButtons (Container content, ActionListener al) {
        return makeDialogPanelWithButtons(content, new OkCancelButtonPane(), al);
    }


//    public static void addPopupMenu(Component comp, String[] labels, String[] cmds, ActionListener listener) {
//
//        final JPopupMenu popup = new JPopupMenu();
//        for (int i = 0; i < labels.length; i++) {
//            JMenuItem m = new JMenuItem(labels[i]);
//            m.setActionCommand(cmds[i]);
//            m.addActionListener(listener);
//            popup.add(m);
//        }
//
//        comp.addMouseListener(new MouseAdapter() {
//
//            public void mousePressed(MouseEvent e) {
//                System.out.println("sss");
//                showPopup(e);
//            }
//
//            public void mouseReleased(MouseEvent e) {
//                System.out.println("sss");
//                showPopup(e);
//            }
//
//            private void showPopup(MouseEvent e) {
//                if (e.isPopupTrigger())
//                    popup.show(e.getComponent(), e.getX(), e.getY());
//            }
//        });
//    }

}


