package org.af.commons.threading;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.af.commons.errorhandling.ErrorHandler;
import org.af.commons.widgets.buttons.OKButtonPane;
import org.af.commons.widgets.buttons.OkCancelButtonPane;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 *
 */
public class ProgressDialog<T, V> extends JDialog implements PropertyChangeListener, ActionListener {
    private static final Log logger = LogFactory.getLog(ProgressDialog.class);

    private static final long serialVersionUID = 1L;

    private final SafeSwingWorker<T, V> task;
    private ProgressPanel<T, V> progressPanel;
    private OkCancelButtonPane buttonPane = new OkCancelButtonPane();
    private final boolean abortable;
    private JPanel jp;

    /**
     *
     * @param task monitored task
     * @param abortable is it ok to abort the task by closing the
     * @param jp 
     */
    public ProgressDialog(JFrame owner, String title, SafeSwingWorker<T, V> task, boolean abortable, JPanel jp) {    	
        super(owner, title);
        this.task = task;
        if (jp == null) jp = new JPanel();
        this.jp = jp;
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
 
    public void execute() {
    	logger.info("Executing: "+this.getTitle());
    	task.execute();
    }

    /**
     * create all stuff
     */
    private void makeComponents() {
        progressPanel = new ProgressPanel<T, V>(task);
        buttonPane = new OkCancelButtonPane("Ok", "Abort");
        buttonPane.setEnabled(0, false);
        buttonPane.setEnabled(1, true);
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

        cp.add(jp, cc.xyw(1, 1, 2));
        cp.add(progressPanel, cc.xyw(1, 3, 2));
        cp.add(buttonPane, cc.xy(2, 5));

        cp.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(cp);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    /**
     * If progress property of SafeSwingWorker reaches 100% we are done and enable the OK button..
     *
     * @param evt event
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            if (task.getProgress() == 100) {
                buttonPane.setEnabled(1, false);
                buttonPane.setEnabled(0, true);
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
            // if button was pressed its enabled, which means we are done
            onExit();
        }
        if (e.getActionCommand().equals(OKButtonPane.CANCEL_CMD)) {
            // if cancel was pressed, onExit still handles it
            onExit();
        }
    }

    /**
     * Tries to shut down the task. If the task is already done, just dispose the frame. If not,
     * and task is not abortable, see if user really wants to quit completely. If abortable, ask about
     * it and then try to cancel the task.
     */
    public void onExit() {
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
                	//TODO CleanUpOnShutDown.getInstance().cleanUpAll();
                }
            }
        }
    }
}
