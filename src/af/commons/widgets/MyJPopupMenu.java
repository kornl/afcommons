package af.commons.widgets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//TODO inspect all subclasses, move some to table popup
public class MyJPopupMenu extends JPopupMenu implements ActionListener {
    private Point invocationPoint;

    public MyJPopupMenu() {
        
    }

    public MyJPopupMenu(String[] labels, String[] cmds) {
        for (int i=0; i<labels.length; i++) {
            JMenuItem item = new JMenuItem(labels[i]);
            item.setActionCommand(cmds[i]);
            add(item);
        }
    }

    public MyJPopupMenu(String[] labels, String[] cmds, ActionListener al) {
        this(labels, cmds);
        addActionListener(al);
    }

    public void addActionListener(ActionListener al) {
        for (int i=0; i<getJMenuItemCount(); i++) {
            getJMenuItem(i).addActionListener(al);
        }
    }

    protected JMenuItem makeMenuItem(String name, String action) {
        return makeMenuItem(name, action, true);
    }

    protected JMenuItem makeMenuItem(String name, String action, boolean enabled) {
        JMenuItem item = new JMenuItem(name);
        item.setActionCommand(action);
        item.addActionListener(this);
        item.setEnabled(enabled);
        return (item);
    }


    public JMenuItem getJMenuItem(int i) {
        return (JMenuItem) getComponent(i);
    }

    public int getJMenuItemCount() {
        return getComponentCount();
    }

    public void show(Component invoker, int x, int y) {
        this.invocationPoint = new Point(x,y);
        super.show(invoker, x, y);
    }

    public Point getInvocationPoint() {
        return invocationPoint;
    }

    public void actionPerformed(ActionEvent e) {
        
    }
}
