package org.af.commons.threading;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import javax.swing.SwingUtilities;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
    Buffer for intermediate results of SafeSwingWorker to publish into.
    Uses Observable pattern so we can update certain GUI elements which
    monitor an object of this class.
 */

public class SwingWorkerMsgBuffer<V> extends Observable {
    private static final Log logger = LogFactory.getLog(SwingWorkerMsgBuffer.class);

    // buffer
    private List<V> buf = new ArrayList<V>();

    /**
     * Writes message into buffer. Called by SafeSwingWorker.publish
     *
     * @param msg message to publish
     */
    public void writeMsg(final V msg) {
        logger.info(msg.toString());
        buf.add(msg);
        // be sure not to update on EDT
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                setChanged();
                notifyObservers(msg);
            }
        });
    }
}
