package org.af.commons.errorhandling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.af.commons.logging.LoggingSystem;
import org.af.commons.logging.widgets.DetailsPanel;
import org.af.commons.threading.SafeSwingWorker;
import org.af.commons.widgets.GUIToolKit;
import org.af.commons.widgets.buttons.OkCancelButtonPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.jxlayer.plaf.ext.LockableUI;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Base class for crit. & recoverable error dialogs. Error dialog is always a modal JFrame.
 * It conatins at least a message, a button to inform the developers and a button to exit.
 * If you provided a DetailsPanelFactory in the ErrorHandler you can switch on the details panel.
 */

abstract public class ErrorDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    protected static final Log logger = LogFactory.getLog(ErrorDialog.class);


    // displayed err msg
    protected final String msg;
    // throwable which caused the error, might be null
    protected final Throwable e;

    // button to inform about the error
    protected final JButton bInform = new JButton("Ok");
    // exit button
    protected final JButton bExit = new JButton("Cancel");
    // display ok button?
    protected final boolean withOkButton;
    
    // header, for err msg
    protected JTextArea taHeader;
    // name of user
    protected JTextField tfName;
    // reply mail of user
    protected JTextField tfEMail;
    // other contact details of user
    protected JTextField tfOtherContact;
    // description of error
    protected JTextArea taDesc;
    // buttons on bottom
    protected OkCancelButtonPane buttonPane;
    // msg in header
    protected String informMsg = null;
    // to disable the whole dialog
    protected LockableUI lockableUI;

    /**
     * Constructor
     *
     * @param msg displayed error msg (don't pass null)
     * @param e   throwably which caused the error (don't pass null)
     * @param fatal is the error a fatal error and the application should be shut down
     */
    public ErrorDialog(String msg, Throwable e, boolean fatal) {
        super(GUIToolKit.findActiveFrame());
        this.msg = msg;
        this.e = e;
        this.withOkButton = fatal;
        // if throwable was given, dump it to logger and std err
        // this prints the msg twice, but it only happens at the the end, so we don't care
        // and we make sure not to let it slip
        if (e != null) {
            e.printStackTrace();
            logger.error("Exception:", e);
        }
        setTitle("Error");

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCloseWindow();
            }
        });
        makeComponents();
        doTheLayout();
        setVisible(true);
        setResizable(true);
        setModal(true);
    }

    /**
     * create and initialize the widgets
     */
    protected void makeComponents() {
        bInform.addActionListener(this);
        bExit.addActionListener(this);
        taHeader = new JTextArea(4,40);
        taHeader.setEditable(false);
        taHeader.setOpaque(false);
        taHeader.setBorder(new EmptyBorder(20,20,20,20));
        taHeader.setText(informMsg);
        taHeader.setWrapStyleWord(true);
        taHeader.setLineWrap(true);
        tfName = new JTextField();
        tfEMail = new JTextField();
        tfOtherContact = new JTextField();
        taDesc = new JTextArea(4,30);
        buttonPane = new OkCancelButtonPane();
        buttonPane.addActionListener(this);
    }


    /**
     * layout the widgets
     * @param withDetails show the details panel?
     */
    protected void doTheLayout() {
    	lockableUI = GUIToolKit.setContentPaneAsLockableJXLayer(getRootPane(), getPanel());
    	
        CellConstraints cc = new CellConstraints();

        JPanel cp = new JPanel();
        String cols = "pref:grow, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref";
        String rows = "pref, 5dlu, fill:pref:grow, 5dlu, pref";
        FormLayout layout = new FormLayout(cols, rows);

        cp.setLayout(layout);

        String msg2 = msg.replaceAll("\n", "<br>");
        cp.add(new JLabel("<html>" + msg2 + "</html>"), cc.xyw(1, 1, 6));
        
        DetailsPanel dd = LoggingSystem.getInstance().makeDetailsPanel();
        cp.add(dd, cc.xyw(1, 3, 6));
        
        cp.add(bExit, cc.xy(4, 5));

        cp.add(bInform, cc.xy(8, 5));
        cp.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(cp);
        pack();
        setLocationRelativeTo(getParent());
    }

    /**
     * dispatch actions from buttons
     *
     * @param e the action event
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bExit) {
            onExit();
        }
        if (e.getSource() == bInform) {
            onInform();
        }
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
                table.put("Name", tfName.getText());
                table.put("Email", tfEMail.getText());
                table.put("Contact", tfOtherContact.getText());
                table.put("Description", taDesc.getText());                
                (new HTTPPoster()).post(ErrorHandler.getInstance().getReportURL(), table, getAttachedFiles());                
                return null;
            }
            @Override
            protected void onSuccess(Void result) {
                JOptionPane.showMessageDialog(ErrorDialog.this, "Report was sent.");
                dispose();
            }

            @Override
            protected void onFailure(Throwable t) {
                String msg = "Could not connect to server and send report.\nPlease send mail manually!";
                logger.error(msg, t);
                JOptionPane.showMessageDialog(ErrorDialog.this, msg);
                lockableUI.setLocked(false);
            }
        };
        worker.execute();
    }

    /**
     * Handler for exit action
     */
    protected void onExit() {
    	dispose();
    }

    /**
     * Handler for ok action
     */
    protected void onOk() {
        dispose();
    }

    /**
     * Handler for closing of window
     */
    protected void onCloseWindow() {
    	dispose();        
    }    
    
    protected JPanel getPanel(){
        JPanel p = new JPanel();
        String cols = "left:pref, 5dlu, pref:grow";
        String rows = "pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, fill:pref:grow, 5dlu, pref";
        FormLayout layout = new FormLayout(cols, rows);

        p.setLayout(layout);
        CellConstraints cc = new CellConstraints();

        int row = 1;
        
        p.add(taHeader,                                                 cc.xyw(1, row, 3));

        row += 2;
        
        p.add(new JLabel("When / How did the error happen?"),           cc.xy(1, row));
        JScrollPane sp1 = new JScrollPane(taDesc);
        p.add(sp1,                                                      cc.xy(3, row));

        row += 2;
        
        p.add(new JLabel("OPTIONAL: If you want to help or get feedback, give us some way to contact you:"), cc.xyw(1, row, 3));
        
        row += 2;
        
        p.add(new JLabel("Optional contact (email, phone)"),            cc.xy(1, row));
        p.add(tfEMail,                                                  cc.xy(3, row));
                
        row += 2;
        
        p.add(getOptionalPanel(),                                       cc.xyw(1, row, 3));
        
        row += 2;

        p.add(buttonPane,                                               cc.xyw(1, 11, 3, "right, bottom"));

        p.setBorder(new EmptyBorder(5,5,5,5));
        return p;
    }

    protected static JPanel getOptionalPanel() {
    	return  new JPanel();
    }
    
    /**
     * Override this if you want to attach some files to the inform message.
     * @return list of files
     */
    protected Hashtable<String, File> getAttachedFiles() throws IOException {
        return new Hashtable<String, File>();
    }
    
}
