package af.commons.widgets.tables;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingx.JXTable;

import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.MouseEvent;

public class JXTable2 extends JXTable {
    protected static final Log logger = LogFactory.getLog(JXTable2.class);

    public JXTable2(TableModel tableModel) {
        super(tableModel);
    }

    /**
     * Single left clicks have a VERY strange behaviour, so we change them to double clicks here.
    */
    protected void processMouseEvent(MouseEvent e) {
    	if (e.getClickCount()==1 && e.getButton() == MouseEvent.BUTTON1) {
    		super.processMouseEvent(new MouseEvent((Component)e.getSource(),
    				e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(),
    				e.getClickCount()+1, false, e.getButton()));
//    		Component editorComponent = getEditorComponent();
//    		if (editorComponent instanceof JTextField) {
//				((JTextField) editorComponent).selectAll();
//			}
    	} else if (e.getButton() == MouseEvent.BUTTON3) {
    		logger.debug("Send new Event.");
    		super.processMouseEvent(new MouseEvent((Component)e.getSource(),
    				 MouseEvent.MOUSE_PRESSED, e.getWhen(), MouseEvent.BUTTON1_MASK, e.getX(), e.getY(),
    				1, false, MouseEvent.BUTTON1));
    		super.processMouseEvent(e);
    	} else {
    		super.processMouseEvent(e);
    	}
    }
}