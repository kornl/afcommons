package af.commons.widgets.tables;

import af.commons.widgets.MyJPopupMenu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TablePopupMenu extends MyJPopupMenu {
    private JTable table;

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

    public void actionPerformed(ActionEvent e) {
        Point p = getInvocationPoint();
        int row = table.rowAtPoint(p);
        int col = table.columnAtPoint(p);
        System.out.println(row + ":" + col);
        if (0 <= row && row < table.getRowCount() && 0 <= col && col < table.getColumnCount())
            actionPerformed(row, col, e.getActionCommand());
    }

    // implement for dealing with (legal) clicks on cells
    protected void actionPerformed(int row, int col, String cmd) {

    }

}
