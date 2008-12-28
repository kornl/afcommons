package af.commons.threading;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;


/**
 * Simple panel to watch the progress of a SafeSwingWorker. Observes SwingWorkerMsgBuffer.
 * Also listens for changes of the progess property of the SafeSwingWorker.
 * Has a label for a heading, a progress bar and a TextArea for detailed messages.
 */


public class ProgressPanel<T,V> extends JPanel implements PropertyChangeListener, Observer {
	private static final Log logger = LogFactory.getLog(ProgressPanel.class);

	private static final long serialVersionUID = 1L;

    // label for headings / status messages
    private JLabel label;
	// obligatory progress bar
    private JProgressBar progressBar;
    // details go here
    private JTextArea textArea;
	// the monitored SwingWorker
    private SafeSwingWorker<T,V> task;


    /**
     * Constructor
     */
    public ProgressPanel() {
        makeComponents();
        doTheLayout();
	}

    /**
     * Constructor
     * @param task monitored SwingWorker
     */
    public ProgressPanel(SafeSwingWorker<T,V> task) {
        setTask(task);

        makeComponents();
        doTheLayout();
	}

    /**
     * set the monitored task
     * @param task monitored SwingWorker
     */
    public void setTask(SafeSwingWorker<T,V> task) {
        this.task = task;
        // listen to updates
        task.addPropertyChangeListener(this);
        task.addMsgBufObserver(this);
    }

    /**
     * construct all stuff
     */
    private void makeComponents() {
        label = new JLabel();

        progressBar = new JProgressBar(0, 100);
        //progressBar.setValue(0);
        progressBar.setIndeterminate(true);
        progressBar.setStringPainted(true);

        textArea = new JTextArea(15, 30);
        textArea.setEditable(false);
    }

    /**
     * layout all stuff
     */
    private void doTheLayout() {
        String cols = "fill:pref:grow";
        String rows = "pref, 5dlu, pref, 5dlu, fill:default:grow, 5dlu, pref";
        FormLayout layout = new FormLayout(cols, rows);

        setLayout(layout);
        CellConstraints cc = new CellConstraints();

        add(label,                              cc.xy (1, 1));
        add(progressBar,                        cc.xy (1, 3));
        add(new JScrollPane(textArea),          cc.xy (1, 5));

        setBorder(new EmptyBorder(10,10,10,10));
    }

//	/*
//        Log running time for debugging.
//	 */
//    private void  logRunningTime(int progress) {
//        Date date = Calendar.getInstance().getTime();
//        long laufzeit = date.getTime() - this.date.getTime();
//        long sec = (laufzeit/1000) % 60;
//        long min = (laufzeit/60000);
//        logger.info("Task ("+progress+"%) is running for "+min+" minutes and "+sec+" seconds.");
//	}

    /*
	    Not really used ATM.
     */
    public JProgressBar getProgressBar() {
        return progressBar;
    }

    /**
     * If progress property of monitored SafeSwingWorker changes update progress bar.
     * @param evt event
     */
	public void propertyChange(PropertyChangeEvent evt) {
		if ("progress".equals(evt.getPropertyName())) {
			if (task.isCancelled()) {
				getProgressBar().setString("");
			} else {
				//String progressType = evt.getNewValue().toString();
				int progress = task.getProgress();
				getProgressBar().setValue(progress);
				if (progress>0) {
					getProgressBar().setIndeterminate(false);
//					getProgressBar().setString(null);
				}
//				if (progress==0) {
//					getProgressBar().setString("");
//				}
                // debug info
//                logRunningTime(progress);
            }
		}
	}

    /**
     * Update panel when observed SwingWorkerMsgBuffer changes.
     * For the moment all intermediate results are converted to Strings
     * (basically only String messages are reasonable)
     * @param o the SwingWorkerMsgBuffer
     * @param arg argument
     */
    public void update(Observable o, Object arg) {
        V msg = (V) arg;
        textArea.append(msg.toString() + "\n");
    }

}
