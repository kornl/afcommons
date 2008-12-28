package af.commons.widgets;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class MyJPopupMenu extends JPopupMenu {
    private Point invocationPoint;

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

    public JMenuItem getJMenuItem(int i) {
        return (JMenuItem) getComponent(i);
    }

    public int getJMenuItemCount() {
        return getComponentCount();
    }

    public void show(Component invoker, int x, int y) {
        super.show(invoker, x, y);
        this.invocationPoint = new Point(x,y);
    }

    public Point getInvocationPoint() {
        return invocationPoint;
    }
}
