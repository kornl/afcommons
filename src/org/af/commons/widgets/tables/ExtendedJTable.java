package org.af.commons.widgets.tables;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.table.TableModel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ExtendedJTable extends JTable{


	/* ***  Members      *****/
    protected int visibleRowCount = 10;
    protected int prefferedWidth = 100;
	protected static final Log logger = LogFactory.getLog(ExtendedJTable.class);

    /* ***  Contructors      *****/

    public ExtendedJTable() {}

    public ExtendedJTable(TableModel tableModel) {
        super(tableModel);
    }

    /**
     * Single left clicks have a VERY strange behaviour, so we change them to double clicks here.
     */ 
    protected void processMouseEvent(MouseEvent e) {
    	if (e.getClickCount()>0) { logEvent(e); }
    	if (e.getClickCount()==1 && e.getButton() == MouseEvent.BUTTON1) {
    		super.processMouseEvent(new MouseEvent((Component)e.getSource(), 
    				e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), 
    				e.getClickCount()+1, false, e.getButton()));
    		Component editorComponent = getEditorComponent();
    		if (editorComponent instanceof JTextField) {
				((JTextField) editorComponent).selectAll();
			}
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
    
    public static void logEvent(MouseEvent e) {
		logger.debug("Source: "+e.getSource()+"\nID: "+e.getID()+"\nWhen: "+e.getWhen()
				+"\nModifiers: "+e.getModifiers()+"\nX: "+e.getX()+"\nY: "+e.getY()
				+"\nClickCount: "+e.getClickCount()+"\nButton: "+e.getButton());
    }
    
    
    public int getVisibleRowCount() {
        return visibleRowCount;
    }

    /* ***  Acessors         *****/
    public Dimension getPreferredScrollableViewportSize() {
        Dimension pref = getPreferredSize();
        if (visibleRowCount != -1) {
            int h = visibleRowCount * getRowHeight() + getTableHeader().getPreferredSize().height+3;
            Dimension d = new Dimension(pref.width, h);
            return d;
        }
        else {
            return pref;
        }
       // return new Dimension(d.width, d.height);
    }

    // for background


    public boolean getScrollableTracksViewportHeight()    {
        Component parent = getParent();

        if (parent instanceof JViewport)
            return parent.getHeight() > getPreferredSize().height;

        return false;
    }

   /* ***  Mutators         *****/
    public void setVisibleRowCount(int visibleRowCount) {
        this.visibleRowCount = visibleRowCount;
    }

    public void setPreferredWidth(int prefferedWidth) {
        this.prefferedWidth = prefferedWidth;

        // set sizes of columns
        int nr = getColumnModel().getColumnCount();
        for (int i=0; i<nr; i++) {
            getColumnModel().getColumn(i).setPreferredWidth(prefferedWidth / nr );
        }
    }

    void setColumnWidths(Graphics g) {
        FontMetrics fontMetrics = g.getFontMetrics();
        int sum=0;
        for (int j=0; j<getColumnCount(); j++) {
            int w_max = 0;
            for (int i=0; i<getRowCount(); i++) {
                String s = getModel().getValueAt(i, j).toString();
                int w = fontMetrics.stringWidth(s);
                w_max = (w > w_max ? w : w_max);
            }
            w_max = w_max +20;
            sum += w_max;
            getColumnModel().getColumn(j).setPreferredWidth(w_max);
            //getColumnModel().getColumn(j).setMinWidth(w_max);
            //getColumnModel().getColumn(j).setWidth(w_max);
            //getColumnModel().getColumn(j).setMaxWidth(w_max);
        }
        //setPreferredSize(new Dimension(sum, 400));
        //setMaximumSize(new Dimension(sum, 400));
    }
    


		

}
