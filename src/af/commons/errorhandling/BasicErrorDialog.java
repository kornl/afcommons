package af.commons.errorhandling;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import af.commons.logging.LoggingSystem;
import af.commons.logging.widgets.DetailsPanel;
import af.commons.widgets.GUIToolKit;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Base class for crit. & recoverable error dialogs. Error dialog is always a modal JFrame.
 * It conatins at least a message, a button to inform the developers and a button to exit.
 * If you provided a DetailsPanelFactory in the ErrorHandler you can switch on the details panel.
 */

abstract public class BasicErrorDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;

    protected static final Log logger = LogFactory.getLog(BasicErrorDialog.class);


    // displayed err msg
    protected final String msg;
    // throwable which caused the error, might be null
    protected final Throwable e;

    // details button
    protected final JButton bDetails = new JButton("Details");
    // button to inform about the error
    protected final JButton bInform = new JButton("Inform");
    // exit button
    protected final JButton bExit = new JButton(getExitButtonLabel());

    /**
     * Constructor (no info on throwable)
     *
     * @param msg displayed error msg (don't pass null)
     */
    protected BasicErrorDialog(String msg) {
        this(msg, null);
    }

    /**
     * Constructor
     *
     * @param msg displayed error msg (don't pass null)
     * @param e   throwably which caused the error (don't pass null)
     */
    protected BasicErrorDialog(String msg, Throwable e) {
        super(GUIToolKit.findActiveFrame());
        this.msg = msg;
        this.e = e;
        setResizable(true);
        setModal(true);
        // if throwable was given, dump it to logger and std err
        // this prints the msg twice, but it only happens at the the end, so we don't care
        // and we make sure not to let it slip
        if (e != null) {
            e.printStackTrace();
            logger.error("Exception:", e);
        }
        setTitle(getDialogTitle());

        // call onExit on close action, derived classes can override
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });
        makeComponents();
        doTheLayout(false);
        setVisible(true);
    }

    /**
     * create and init. the widgets
     */
    private void makeComponents() {
        bDetails.addActionListener(this);
        bInform.addActionListener(this);
        bExit.addActionListener(this);
    }


    /**
     * layout the widgets
     * @param withDetails show the details panel?
     */
    protected void doTheLayout(boolean withDetails) {

        CellConstraints cc = new CellConstraints();

        JPanel cp = new JPanel();
        String cols = "pref:grow, pref, 5dlu, pref, 5dlu, pref";
        String rows = "pref, 5dlu, fill:pref:grow, 5dlu, pref";
        FormLayout layout = new FormLayout(cols, rows);

        cp.setLayout(layout);

        String msg2 = msg.replaceAll("\n", "<br>");
        cp.add(new JLabel("<html>" + msg2 + "</html>"), cc.xyw(1, 1, 6));
        if (withDetails) {
            DetailsPanel dd = LoggingSystem.getInstance().makeDetailsPanel();
            cp.add(dd, cc.xyw(1, 3, 6));
        }
        cp.add(bExit, cc.xy(2, 5));
        // only show button if details are not present and a factory was supplied
        if (!withDetails)
            cp.add(bDetails, cc.xy(4, 5));
        cp.add(bInform, cc.xy(6, 5));
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
        if (e.getSource() == bDetails) {
            onDetails();
        }
        if (e.getSource() == bExit) {
            onExit();
        }
        if (e.getSource() == bInform) {
            onInform();
        }
    }

    /**
     * handler for details button
     */
    protected void onDetails() {
        doTheLayout(true);
    }

    /**
     * handler for inform button
     */
    protected void onInform() {
        ErrorHandler.getInstance().makeInformDialog(this);
    }

    /**
     * handler for exit action
     */
    protected void onExit() {
        dispose();
    }

    /**
     * @return label for the leftmost button, either "ok" or "exit", etc
     */
    abstract protected String getExitButtonLabel();

    /**
     * @return title for the dialog
     */
    abstract protected String getDialogTitle();

}
