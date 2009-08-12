package org.af.commons.errorhandling;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.af.commons.threading.SafeSwingWorker;
import org.af.commons.widgets.GUIToolKit;
import org.af.commons.widgets.buttons.HorizontalButtonPane;
import org.af.commons.widgets.buttons.OkCancelButtonPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.jxlayer.plaf.ext.LockableUI;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Dialog to inform the developers about a bug (called by the error dialogs).
 */

public class InformDialog extends JDialog implements ActionListener{
    private static final long serialVersionUID = 1L;
    private static final Log logger = LogFactory.getLog(InformDialog.class);

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
    protected final String informMsg;
    // to disable the whole dialog
    protected LockableUI lockableUI;
    
    protected String developerAdress;
    
    public String urlString;

    protected InformDialog(Component parent, String developerAdress, String urlString) {
    	this(parent, developerAdress, urlString, null);
    }
    
    protected InformDialog(Component parent, String developerAdress, String urlString, String informMsg) {
    	this.urlString = urlString;
        this.informMsg = informMsg!=null?informMsg:"An error occured!  Please inform " + developerAdress +
                " about the possible bug. If you have internet access you can probably use the form below. " +
                "Otherwise send a mail manually with a short description.";
        setModal(true);
        this.developerAdress = developerAdress;

        setTitle("Inform about bug");

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        makeComponents();
        doTheLayout();
        pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

	protected void makeComponents() {
        // TODO make this a new widget? multiline-label?
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

    protected JPanel getPanel(){
        JPanel p = new JPanel();
        String cols = "left:pref, 5dlu, pref:grow";
        // two additional rows to add something in subclasses, otherwise have to redo layout completely
        String rows = "pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, pref, 5dlu, fill:pref:grow, 5dlu, pref";
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

        p.add(new JLabel("Your name"),                                  cc.xy(1, row));
        p.add(tfName,                                                   cc.xy(3, row));
        
        row += 2;

        p.add(new JLabel("Email address"),                              cc.xy(1, row));
        p.add(tfEMail,                                                  cc.xy(3, row));
        
        row += 2;

        p.add(new JLabel("Other way of contact(e.g phone)"),            cc.xy(1, row));
        p.add(tfOtherContact,                                           cc.xy(3, row));
        
        row += 2;

        p.add(buttonPane,                                               cc.xyw(1, 15, 3, "right, bottom"));

        p.setBorder(new EmptyBorder(5,5,5,5));
        return p;
    }

    protected void doTheLayout() {
        lockableUI = GUIToolKit.setContentPaneAsLockableJXLayer(getRootPane(), getPanel());
    }

    /**
     * Override this if you want to attach some files to the inform message.
     * @return list of files
     */
    protected Hashtable<String, File> getAttachedFiles() throws IOException {
        return new Hashtable<String, File>();
    }


    /**
     * dispatch actions from buttons
     *
     * @param e the action event
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(HorizontalButtonPane.OK_CMD)) {
            onSend();
        }
        if (e.getActionCommand().equals(HorizontalButtonPane.CANCEL_CMD)) {
            onExit();
        }
    }

    /**
     * handler for sending action
     * just close dialog
     */
    private void onSend() {
        lockableUI.setLocked(true);

        SafeSwingWorker<Void, Void> worker = new SafeSwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {                
                Hashtable<String,String> table = new Hashtable<String,String>();
                table.put("Name", tfName.getText());
                table.put("Email", tfEMail.getText());
                table.put("Contact", tfOtherContact.getText());
                table.put("Description", taDesc.getText());                
                (new HTTPPoster()).post(urlString, table, getAttachedFiles());                
                return null;
            }
            @Override
            protected void onSuccess(Void result) {
                JOptionPane.showMessageDialog(InformDialog.this, "Report was sent.");
                dispose();
            }

            @Override
            protected void onFailure(Throwable t) {
                String msg = "Could not connect to mail server and send mail.\nPlease send mail manually!";
                logger.error(msg, t);
                JOptionPane.showMessageDialog(InformDialog.this, msg);
                lockableUI.setLocked(false);
            }
        };
        worker.execute();
    }

    /**
     * handler for exit action
     * just close dialog
     */
    private void onExit() {
        dispose();
    }
}




