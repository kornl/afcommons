package org.af.commons.widgets;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

public class RightClickTextMenuListener extends MouseAdapter {
	
	    private JPopupMenu pm = new JPopupMenu();
	    private JTextComponent tc;
	    
    	Action copyAction = null;
    	Action cutAction = null;
    	Action pasteAction = null;    	
    	Action selectAllAction = null;
    	
	    public RightClickTextMenuListener(JTextComponent tc) {
	    	this.tc = tc;
	    	
	    	for (Action a : tc.getActions()) {
	    		//System.out.println(a.getValue(Action.NAME));
	    		if (a.getValue(Action.NAME).equals(DefaultEditorKit.copyAction)) copyAction = a;
	    		if (a.getValue(Action.NAME).equals(DefaultEditorKit.cutAction))	cutAction = a;
	    		if (a.getValue(Action.NAME).equals(DefaultEditorKit.pasteAction)) pasteAction = a;
	    		if (a.getValue(Action.NAME).equals(DefaultEditorKit.selectAllAction)) selectAllAction = a;
	    	}

	    	if (copyAction!=null) {
	    		copyAction.putValue(Action.NAME, "Copy");
	    		pm.add(copyAction);
	    	}
	    	if (cutAction!=null) {
	    		cutAction.putValue(Action.NAME, "Cut");
	    		pm.add(cutAction);
	    	}
	    	if (pasteAction!=null) {
	    		pasteAction.putValue(Action.NAME, "Paste");
	    		pm.add(pasteAction);
	    	}
	    	if (selectAllAction!=null) {
	    		selectAllAction.putValue(Action.NAME, "Select All");
	    		pm.addSeparator();
		    	pm.add(selectAllAction);
	    	}
	    	
	    }

	    public void mouseClicked(MouseEvent e) {
	        if (e.getModifiers() == InputEvent.BUTTON3_MASK) {
	            if (!tc.isEnabled()) {
	                return;
	            }
	            
	            cutAction.setEnabled(tc.isEditable() && tc.getSelectedText() != null);
	            copyAction.setEnabled(tc.getSelectedText() != null);
	            pasteAction.setEnabled(tc.isEditable() && Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null).isDataFlavorSupported(DataFlavor.stringFlavor));

	            pm.show(e.getComponent(), e.getX(), e.getY());
	        }
	    }

}
