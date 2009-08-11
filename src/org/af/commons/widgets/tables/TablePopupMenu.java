package org.af.commons.widgets.tables;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTable;

import org.af.commons.widgets.MyJPopupMenu;


public class TablePopupMenu extends MyJPopupMenu {
    private JTable table;

    public TablePopupMenu(JTable table) {
        this.table = table;
    }

    public TablePopupMenu(JTable table, String[] labels, String[] cmds) {
        super(labels, cmds);
        this.table = table;
    }

    public TablePopupMenu(JTable table, String[] labels, String[] cmds, ActionListener al) {
        super(labels, cmds, al);
        this.table = table;
    }

    public JTable getTable() {
        return table;
    }

    public boolean isPointOnCell(Point p) {
        if (!table.contains(p))
            return false;
        int row = table.rowAtPoint(p);
        int col = table.columnAtPoint(p);
        return 0 <= row && row < table.getRowCount() && 0 <= col && col < table.getColumnCount();
    }

    @Override
    public void show(Component invoker, int x, int y) {
        if (isPointOnCell(new Point(x,y)))
            super.show(invoker, x, y);
    }

    public void actionPerformed(ActionEvent e) {
        if (isPointOnCell(getInvocationPoint())) {
            Point p = getInvocationPoint();
            int row = table.rowAtPoint(p);
            int col = table.columnAtPoint(p);
            actionPerformed(row, col, e.getActionCommand());
        }
    }

    // implement for dealing with (legal) clicks on cells
    protected void actionPerformed(int row, int col, String cmd) {

    }

}
