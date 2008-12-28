//package biostat.widgettoolkit.tables;
//
//import javax.swing.table.JTableHeader;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//abstract public class TableHeaderMouseListener extends MouseAdapter {
//
//    private JTableHeader header;
//
//    public TableHeaderMouseListener(JTableHeader header) {
//        this.header = header;
//    }
//
//    abstract protected void actionPerformed(MouseEvent e, int col);
//
//    protected void maybeActionPerformed(MouseEvent e) {
//        if (e.isPopupTrigger()) {
//            int col = header.columnAtPoint(e.getPoint());
//            if (col >= 0 && col <= header.getColumnModel().getColumnCount()) {
//                actionPerformed(e, col);
//            }
//        }
//    }
//
//    public void mousePressed(MouseEvent e) {
//        maybeActionPerformed(e);
//    }
//
//    public void mouseReleased(MouseEvent e) {
//        maybeActionPerformed(e);
//    }
//}
