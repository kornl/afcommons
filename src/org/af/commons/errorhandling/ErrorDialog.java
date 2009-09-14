package org.af.commons.errorhandling;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import org.af.commons.Localizer;
import org.af.commons.io.FileTools;
import org.af.commons.threading.SafeSwingWorker;
import org.af.commons.widgets.GUIToolKit;
import org.af.commons.widgets.MultiLineLabel;
import org.af.commons.widgets.WidgetFactory;
import org.af.commons.widgets.buttons.HorizontalButtonPane;
import org.af.commons.widgets.buttons.OkCancelButtonPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.jxlayer.plaf.ext.LockableUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Base class for critical and recoverable error dialogs.
 * You can set the class which should be used as ErrorDialog.
 */

public class ErrorDialog extends JDialog implements ActionListener {

    protected static final Log logger = LogFactory.getLog(ErrorDialog.class);

    // displayed error message
    protected final String msg;
    // throwable which caused the error, might be null
    protected final Throwable e;

    // is this a fatal error?
    protected final boolean fatal;
    
    // header, for error message
    protected MultiLineLabel taHeader;
    // other contact details of user
    protected JTextField tfEMail;
    // description of error
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
    public ErrorDialog(String msg, Throwable e, boolean fatal) {
        super(GUIToolKit.findActiveFrame());
        this.msg = msg;
        this.e = e;
        this.fatal = fatal;
        // if throwable was given, dump it to logger and std err
        // this prints the msg twice, but it only happens at the the end, so we don't care
        // and we make sure not to let it slip
        if (e != null) {
            e.printStackTrace();
            logger.error("Exception:", e);
        }
        setTitle(Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_TITLE"));

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });
        makeComponents();
        doTheLayout();
        setVisible(true);
        setResizable(true);
        setModal(true);
    }

    /**
     * Create and initialize the widgets.
     */
    private void makeComponents() {
        taHeader = new MultiLineLabel(informMsg);
        tfEMail = new JTextField();
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        for (File file : files.values()) {
        	JTextArea textArea = new JTextArea(5, 10);
            try {
				textArea.setText(FileTools.readFileAsString(file));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            textArea.setEditable(false);
            dd.add(file.getName(), new JScrollPane(textArea));
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

    /**
     * Handler for inform button
     */
    protected void onInform() {
        lockableUI.setLocked(true);

        SafeSwingWorker<Void, Void> worker = new SafeSwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {                
            	Hashtable<String,String> table = new Hashtable<String,String>();
            	table.put("Contact", tfEMail.getText());
            	try {
            		table.put("Shortinfo", "User "+ System.getProperty("user.name", "<unknown user name>")+ 
                    		" with Java "+System.getProperty("java.version", "<unknown java version>")+ 
                    		" from "+ System.getProperty( "java.vm.vendor", "<unknown vendor>" )+
                    		" on "+System.getProperty("os.name", "<unknown OS>")+" / "+ System.getProperty("os.arch", "<unknown OS architecture>")+
                    		"; Language: "+System.getProperty("user.language", "<unknown language>")+
                    		", Desktop: "+System.getProperty("sun.desktop", "<unknown desktop>")+".");
            	} catch (Exception e) {
            		// It is totally okay to ignore errors here...
            		e.printStackTrace();            		
            	}
            	table.put("Description", taDesc.getText());                
            	(new HTTPPoster()).post(ErrorHandler.getInstance().getReportURL(), table, getAttachedFiles());                
            	return null;
            }
            @Override
            protected void onSuccess(Void result) {
                JOptionPane.showMessageDialog(ErrorDialog.this, Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_REPORT"));
                dispose();
            }

            @Override
            protected void onFailure(Throwable t) {
                String msg = Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_PLEASE_MAIL");
                logger.error(msg, t);
                JOptionPane.showMessageDialog(ErrorDialog.this, msg);
                lockableUI.setLocked(false);
            }
        };
        worker.execute();
    }
    
    protected JPanel getPanel() {
        JPanel p = new JPanel();
//        String cols = "left:pref, 5dlu, f:d:g";
        String cols = "left:pref, 5dlu, f:d:g";
        String rows = "pref, 5dlu, f:p:g, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref";
        FormLayout layout = new FormLayout(cols, rows);

        p.setLayout(layout);
        CellConstraints cc = new CellConstraints();

        int row = 1;
        
        String msg2 = msg.replaceAll("\n", "<br>");
        p.add(new JLabel("<html>" + msg2 + "</html>"),                  cc.xyw(1, row, 3));

        p.add(taHeader,                                                 cc.xyw(1, row, 3));

        row += 2;

        p.add(new JLabel(Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_ERRORDESCRIPTION")),           cc.xy(1, row));
        JScrollPane sp1 = new JScrollPane(taDesc);
        p.add(sp1,                                                      cc.xy(3, row));

        row += 2;

        p.add(new JLabel(Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_OPTIONAL")), cc.xyw(1, row, 3));

        row += 2;

        p.add(new JLabel(Localizer.getInstance().getString("AFCOMMONS_ERRORHANDLING_ERRORDIALOG_CONTACT")),            cc.xy(1, row));
        p.add(tfEMail,                                                  cc.xy(3, row));

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
    
}
