package org.af.commons.errorhandling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Hashtable;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.af.commons.Localizer;
import org.af.commons.io.FileTools;
import org.af.commons.threading.SafeSwingWorker;
import org.af.commons.tools.OSTools;
import org.af.commons.widgets.GUIToolKit;
import org.af.commons.widgets.MultiLineLabel;
import org.af.commons.widgets.WidgetFactory;
import org.af.commons.widgets.buttons.HorizontalButtonPane;
import org.af.commons.widgets.buttons.OkCancelButtonPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.jxlayer.plaf.ext.LockableUI;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Base class for critical and recoverable error dialogs.
 * You can set the class which should be used as ErrorDialog.
 */

public class InformDialog extends JDialog implements ActionListener {

    protected static final Log logger = LogFactory.getLog(InformDialog.class);

    // displayed error message
    protected final String msg;
    
    // header, for error message
    protected MultiLineLabel taHeader;
    // other contact details of user
    protected JTextField tfContact;
    // description
    protected JTextArea taDesc;
    // message in header
    protected String informMsg = "";
    // to disable the whole dialog
    protected LockableUI lockableUI;

    /**
     * Constructor
     *
     * @param msg displayed error message (don't pass null)
     * @param e   Throwable which caused the error (don't pass null)
     * @param fatal is the error a fatal error and the application should be shut down
     */
    public InformDialog(String msg) {
        super(GUIToolKit.findActiveFrame(), true);        
        this.msg = msg;           
    }
    
    public void showDialog() {
    	setTitle(getDialogTitle());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });
    	makeComponents();
    	doTheLayout();
    	setResizable(true);
    	setAlwaysOnTop(true);
    	setVisible(true);
    }
    
    protected String getDialogTitle() {
    	return msg;
    }

    /**
     * Create and initialize the widgets.
     */
    private void makeComponents() {
        taHeader = new MultiLineLabel(informMsg);
        tfContact = new JTextField();
        taDesc = new JTextArea(4,30);
    }


    /**
     * Arranges the widgets.
     */
    private void doTheLayout() {
    	lockableUI = GUIToolKit.setContentPaneAsLockableJXLayer(getRootPane(), getPanel());

        JTabbedPane dd = new JTabbedPane();
        dd.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        dd.add(Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_REPORT"), getPanel());
        
        Hashtable<String, File> files = new Hashtable<String, File>();
        try {
			files = getAttachedFiles();
            for (File file : files.values()) {
                JTextArea textArea = new JTextArea(5, 10);
                textArea.setText(FileTools.readFileAsString(file));
                textArea.setEditable(false);
                dd.add(file.getName(), new JScrollPane(textArea));
            }
        } catch (IOException e) {
			JOptionPane.showMessageDialog(this, Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_IOERR"));
		}
        
        HorizontalButtonPane bp = new OkCancelButtonPane(
                Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_INFORM"),
                Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_EXIT"));
        setContentPane(WidgetFactory.makeDialogPanelWithButtons(dd, bp, this));
        pack();
        setLocationRelativeTo(getParent());
    }

    /**
     * Dispatch actions from buttons
     *
     * @param e the action event
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(HorizontalButtonPane.CANCEL_CMD)) {
            onExit();
        }
        if (e.getActionCommand().equals(HorizontalButtonPane.OK_CMD)) {
            onInform();
        }
    }
    

    /**
     * Handler for exit action. Overwrite this method if you want another behavior.
     */
    protected void onExit() {
    	dispose();
    }

    /**
     * Handler for inform button
     */
    protected void onInform() {
        lockableUI.setLocked(true);

        SafeSwingWorker<Void, Void> worker = new SafeSwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
            	(new HTTPPoster()).post(ErrorHandler.getInstance().getReportURL(), getInfoTable(), getAttachedFiles());                
            	return null;
            }
            
			@Override
            protected void onSuccess(Void result) {
                JOptionPane.showMessageDialog(InformDialog.this, Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_REPORTSENT"));
                dispose();
            }

            @Override
            protected void onFailure(Throwable t) {
                String msg = Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_PLEASE_MAIL");
                logger.error(msg, t);
                JOptionPane.showMessageDialog(InformDialog.this, msg);
                lockableUI.setLocked(false);
                
                // Open mail client in Java 6:
                String subject = "Error%20report";
                String body = "Description%20and%20contact%20information:";
                String mailtoURI = "mailto:"+ErrorHandler.getInstance().getDeveloperAddress()+"?SUBJECT="+subject+"&BODY="+body;

                /* This is a Wrapper for Desktop.getDesktop().mail(uriMailTo);
                 * that will do that for Java >=6 and nothing for
                 * Java 5.
                 */    
        		try {	
        			URI uriMailTo = new URI(mailtoURI);
        			Method main = Class.forName("java.awt.Desktop").getDeclaredMethod("getDesktop");
        			Object obj = main.invoke(new Object[0]);
        			Method second = obj.getClass().getDeclaredMethod("mail", new Class[] { URI.class }); 
        			second.invoke(obj, uriMailTo);
        		} catch (Exception e) {			
        			logger.warn("No Desktop class in Java 5 or URI error.",e);
        		}
            }
        };
        worker.execute();
    }
    
    protected JPanel getPanel() {
        JPanel p = new JPanel();
        String cols = "left:pref, 5dlu, f:d:g";
        String rows = "pref, 5dlu, f:p:g, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref";
        FormLayout layout = new FormLayout(cols, rows);

        p.setLayout(layout);
        CellConstraints cc = new CellConstraints();

        int row = 1;
        
        p.add(new JLabel("<html>" + msg.replaceAll("\n", "<br>") + "</html>"),                  cc.xyw(1, row, 3));

        p.add(taHeader,                                                 cc.xyw(1, row, 3));

        row += 2;

        p.add(new JLabel(Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_ERRORDESCRIPTION")),           cc.xy(1, row));
        JScrollPane sp1 = new JScrollPane(taDesc);
        p.add(sp1,                                                      cc.xy(3, row));

        row += 2;

        p.add(new JLabel(Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_OPTIONAL")), cc.xyw(1, row, 3));

        row += 2;

        p.add(new JLabel(Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_CONTACT")),            cc.xy(1, row));
        p.add(tfContact,                                                  cc.xy(3, row));

        row += 2;

        p.add(getOptionalPanel(),                                       cc.xyw(1, row, 3));

        return p;
    }
    
    /**
     * Override this if you want to add an additional panel to the report panel.
     * @return the optional JPanel for the report panel
     */
    protected JPanel getOptionalPanel() {
    	return new JPanel();
    }
    
    /**
     * Override this if you want to attach some files to the inform message.
     * @return Hashtable of files to attach for the report
     */
    protected Hashtable<String, File> getAttachedFiles() throws IOException {
        return new Hashtable<String, File>();
    }
    
    protected Hashtable<String, String> getInfoTable() {
    	Hashtable<String,String> table = new Hashtable<String,String>();
    	table.put("Contact", tfContact.getText());
    	try {
    		table.put("Shortinfo", OSTools.getShortInfo());
    	} catch (Exception e) {
    		// It is totally okay to ignore errors here... (btw. there should be no error, but better safe than sorry)
    		e.printStackTrace();            		
    	}
    	table.put("Description", taDesc.getText());  
		return table;
	}
    
}
