package af.commons.widgets.tables;

import java.awt.Dimension;

import javax.swing.table.DefaultTableModel;

class RowHeader extends ExtendedJTable {

	private static final long serialVersionUID = 1L;

	public RowHeader(ExtendedJTable table, String[] rowNames) {
        Object[][] data = new Object[rowNames.length][1];
         for (int i=0; i<rowNames.length; i++)
            data[i][0] = rowNames[i];
        Object[] columnNames = {""};

        setModel(new Model(data, columnNames));

        setIntercellSpacing(new Dimension(0, 0));
        setCellSelectionEnabled(false);
        setColumnSelectionAllowed(false);
        setRowSelectionAllowed(false);
        setDragEnabled(false);
        getTableHeader().setReorderingAllowed(false);

        setVisibleRowCount(table.getVisibleRowCount());
        //setPreferredWidth(80);
        setDefaultRenderer(Object.class, table.getTableHeader().getDefaultRenderer());
        setRowHeight(table.getRowHeight());
    }

    class Model extends DefaultTableModel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Model(Object[][] objects, Object[] objects1) {
            super(objects, objects1);
        }

        public boolean isCellEditable(int row, int col) {
            return false;
        }
    }
}
