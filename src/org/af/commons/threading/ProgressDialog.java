package org.af.commons.threading;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import org.af.commons.errorhandling.ErrorHandler;
import org.af.commons.widgets.buttons.OKButtonPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Dialog that visualizes the progess of a SafeSwingworker by using a ProgressPanel.
 * The task is automatically started when the dialog is made visible.
 * @param <T> Result type of SafeSwingWorker
 * @param <V> Intermediate type of SafeSwingWorker
 */
public class ProgressDialog<T, V> extends JDialog implements PropertyChangeListener, ActionListener {
    private static final Log logger = LogFactory.getLog(ProgressDialog.class);

    private final SafeSwingWorker<T, V> task;
    private ProgressPanel<T, V> progressPanel;
    private OKButtonPane buttonPane = new OKButtonPane();
    private JPanel extraPanel;
    private Component parent;
    private final boolean abortable;
    private boolean closeOnFinish = false;


    /**
     * Constructor
     * @param parent Parent Component to place the dialog on.
     * @param title Title of the dialog.
     * @param task Monitored task.
     * @param modal Use a modal dialog?
     * @param abortable Allowed to abort the task by closing the dialog? If not a warning is displayed that the application will exit.
     * @param extraPanel Additional panel, displayed above the message Panel.
     */
    public ProgressDialog(Component parent, String title, SafeSwingWorker<T, V> task,
                          boolean modal, boolean abortable, JPanel extraPanel) {
        super(SwingUtilities.getWindowAncestor(parent), title);
        setModal(modal);
        this.parent = parent;
        this.task = task;
        if (extraPanel == null) extraPanel = new JPanel();
        this.extraPanel = extraPanel;
        task.addPropertyChangeListener(this);
        this.abortable = abortable;

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onExit();
            }
        });

        makeComponents();
        doTheLayout();
    }
 

    /**
     * Constructor
     * @param parent Parent Component to place the dialog on.
     * @param title Title of the dialog.
     * @param task Monitored task.
     * @param modal Use a modal dialog?
     * @param abortable Allowed to abort the task by closing the dialog? If not a warning is displayed that the application will exit.
     */
    public ProgressDialog(Component parent, String title, SafeSwingWorker<T, V> task,
                          boolean modal, boolean abortable) {
        this(parent, title, task, modal, abortable, new JPanel());
    }


    /**
     * create all stuff
     */
    private void makeComponents() {
        progressPanel = new ProgressPanel<T, V>(task);
        buttonPane = new OKButtonPane("Abort");
        buttonPane.setEnabled(true);
        buttonPane.addActionListener(this);
    }

    /**
     * layout all stuff
     */
    private void doTheLayout() {

        JPanel cp = new JPanel();
        String cols = "fill:pref:grow, right:pref";
        String rows = "pref, 10dlu, pref, 10dlu, pref";
        FormLayout layout = new FormLayout(cols, rows);

        cp.setLayout(layout);
        CellConstraints cc = new CellConstraints();

        cp.add(extraPanel, cc.xyw(1, 1, 2));
        cp.add(progressPanel, cc.xyw(1, 3, 2));
        cp.add(buttonPane, cc.xy(2, 5));

        cp.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(cp);

        pack();
        setLocationRelativeTo(parent);
    }

    /**
     * If progress property of SafeSwingWorker reaches 100% we are done and enable the OK button.
     * @param evt event
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            if (task.getProgress() == 100) {
                onTaskFinished();
            }
        }
    }

    /**
     * Action handling for OK button.
     *
     * @param e event
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(OKButtonPane.OK_CMD)) {
            onExit();
        }
    }

    /**
     * Should the dialog automatically be closed when the task reaches 100% ?
     * @return True iff the dialog is automatically closed when the task reaches 100%.
     */
    public boolean isCloseOnFinish() {
        return closeOnFinish;
    }

    /**
     * Set to true if the dialog should automatically be closed when the task reaches 100%.
     * @param closeOnFinish True iff the dialog is automatically closed when the task reaches 100%.
     */
    public void setCloseOnFinish(boolean closeOnFinish) {
        this.closeOnFinish = closeOnFinish;
    }

    /**
     * Called when task reaches 100%.
     * Default behaviour is to enable the Ok button and disable the cancel button.
     * If closeOnFinish is set to true, the dialog is automatically disposed.
     */
    protected void onTaskFinished() {
        if (closeOnFinish) {
            dispose();
        } else {
            buttonPane.setText("Finish");
        }
    }

    /**
     * Tries to shut down the task. If the task is already done, just dispose the frame. If not,
     * and task is not abortable, see if user really wants to quit completely. If abortable, ask about
     * it and then try to cancel the task.
     */
    protected void onExit() {
        if (task.isDone()) {
            // we are done, everything ok
            dispose();
        } else {
            if (abortable) {
                // really abort?
                int i = JOptionPane.showConfirmDialog(this, "Really abort this task?", "Abort?", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                    int tries = 100;
                    InterruptedException interrupt = null;
                    try {
                        // try to cancel the task
                        while (!task.cancel(true) && --tries > 0) {
                            Thread.sleep(100);
                        }
                    } catch (InterruptedException e) {
                        interrupt = e;
                    }
                    // we could not cancel for some reason
                    if (tries <= 0 || interrupt != null) {
                        ErrorHandler.getInstance().makeRecovErrDialog("Could not abort this task!",
                                interrupt == null ? new RuntimeException() : interrupt);
                    } else {
                        dispose();
                    }
                }
            } else {
                // cant abort, really quit?
                int i = JOptionPane.showConfirmDialog(this, "This task is not abortable!\nReally quit program?", "Quit?", JOptionPane.YES_NO_OPTION);
                if (i == JOptionPane.YES_OPTION) {
                	criticalAbort();
                }
            }
        }
    }

    @Override
    public void setVisible(boolean b) {
        if (b)
            task.execute();
        super.setVisible(b);
    }

    /**
     * Overwrite this to define what happens when the user insists on aborting an non-abortable task.
     */
    protected void criticalAbort() {
        System.exit(1);
    }
}
