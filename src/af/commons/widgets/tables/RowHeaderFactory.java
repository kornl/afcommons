package af.commons.widgets.tables;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;


public class RowHeaderFactory {

    
    /* ***  Methods         *****/
    public static JScrollPane makeRowHeader(ExtendedJTable table, String[] rowNames) {
        DefaultTableModel headerData = new DefaultTableModel(0, 1);

        for (int i = 0; i < rowNames.length; i++) {
            headerData.addRow(new Object[] { rowNames[i] } );
        }

        RowHeader rowHeader = new RowHeader(table, rowNames);


        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setRowHeaderView(rowHeader);

        JTableHeader corner = rowHeader.getTableHeader();
        corner.setReorderingAllowed(false);
        corner.setResizingAllowed(false);

        scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, corner);

        return scrollPane;
    }
}


