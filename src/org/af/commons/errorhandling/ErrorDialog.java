package org.af.commons.errorhandling;

import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JOptionPane;

import org.af.commons.Localizer;
import org.af.commons.tools.StringTools;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Base class for critical and recoverable error dialogs.
 * You can set the class which should be used as ErrorDialog.
 */

public class ErrorDialog extends InformDialog implements ActionListener {

    protected static final Log logger = LogFactory.getLog(ErrorDialog.class);

    // throwable which caused the error, might be null
    protected final Object e;

    // is this a fatal error?
    protected final boolean fatal;

    /**
     * Constructor
     *
     * @param msg displayed error message (don't pass null)
     * @param e   Throwable which caused the error (don't pass null)
     * @param fatal is the error a fatal error and the application should be shut down
     */
    public ErrorDialog(String msg, Object e, boolean fatal) {
        super(msg);
        this.e = e;
        this.fatal = fatal;
        // if throwable was given, dump it to logger and standard error
        // this prints the message twice, but it only happens at the the end, so we don't care
        // and we make sure not to let it slip
        if (e != null) {
        	if (e instanceof Throwable) {
        		((Throwable)e).printStackTrace();
        		logger.error("Exception:", ((Throwable)e));
        	} else {
        		System.out.println(e.toString());
        		logger.error("Error: "+e.toString());
        	}
        }
    }
    
    protected String getDialogTitle() {
    	return Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_TITLE")+": "+msg;
    }

    /**
     * Handler for exit action. Overwrite this method if you want another behavior.
     */
    protected void onExit() {
    	if (fatal) {
    		int answer = JOptionPane.showConfirmDialog(this, Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_CONFIRM"));
    		if (answer == JOptionPane.OK_OPTION) {
    			onShutdown();
    		}
    	}
    	dispose();
    }

    /**
     * Handler for shutdown. Overwrite this method if you want to do some cleanup.
     * The default is a call of <code>System.exit(1);</code>.
     */
    protected void onShutdown() {
		System.exit(1);		
	} 
    
    protected Hashtable<String, String> getInfoTable() {
    	Hashtable<String, String> table = super.getInfoTable();
    	if (e!=null) {
    		if (e instanceof Throwable) {
    			table.put("A StackTrace", StringTools.stackTraceToString(((Throwable)e)));
    			if (((Throwable)e).getMessage()!=null) {
    				table.put("Error message", ((Throwable)e).getMessage());
    			}    			
    		} else  {
    			table.put("Error", e.toString());
    		}
    	}
    	return table;
    }
  
}
