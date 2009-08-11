package org.af.commons.threading;

import java.util.List;
import java.util.Observer;
import java.util.concurrent.CancellationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdesktop.swingworker.SwingWorker;


/**
 * This SwingWorker handles exceptions thrown by the doInBackground() method.
 * There is also a mechanism for cancelation and message publishing.
 * It divides the done() method in submethods onSuccess(), onFailure() and onCancel()
 * which could be later overridden.
 * You can also register a SwingWorkerObserver, which is notified when the worker thread is done.
 */

public abstract class SafeSwingWorker<T, V> extends SwingWorker<T, V> {
	
	protected static final Log logger = LogFactory.getLog(SafeSwingWorker.class);
    // observable buffer
	protected SwingWorkerMsgBuffer<V> msgBuffer = new SwingWorkerMsgBuffer<V>();
    // observing object which wants to be called when worker is finished
    protected SwingWorkerObserver observer = null;


    /**
     * The done() method is declared as final so it is not reused by inheritance.
     * The programmer would rather define the onSucces(), onFailure() and onCancel() methods.
     *
     */
    @Override
    protected final void done() {
        try {
            onSuccess(get());
        } catch (CancellationException e) {
            onCancel();
        } catch (Throwable e) {
            // any other error --> failure
            onFailure(e);
        }
        if (observer != null) {
            observer.afterSwingWorkerDone();
        }
    }


    /**
     * This method is called within the Event Dispatch Thread when the
     * doInBackGround() method successfully complete.
     *
     * @param result computed value returned by the doInBackGround() method.
     */
    protected abstract void onSuccess(T result);

    /**
     * This method is called within the EDT when the doInBackground() method
     * throws an exception.
     *
     * @param t thrown by doInBackground()
     */
    protected void onFailure(Throwable t) {
        // dont know what to do with exception, pass it to the DefaultHandler
        // if you dont want this, override
        /* DefaultExceptionHandler deh = new DefaultExceptionHandler();
        deh.handle(t); */
    	throw new RuntimeException(t);
    }

    /**
     * This method is called within the EDT when the doInBackground() method
     * is canceled thru the cancel method of SwingWorker
     * Maybe you want to do clean up here / free resources?
     */
    protected void onCancel() {}

    /**
     * Add an observer to the SwingWorkerMsgBuffer so we can watch the progress of the thread.
     * @param obs obeserver
     */
    public void addMsgBufObserver(Observer obs) {
        msgBuffer.addObserver(obs);
    }

    /**
     * Sets the observer which is notified when the thread is done.
     * @param observer obeserver
     */
    public void setObserver(SwingWorkerObserver observer) {
        this.observer = observer;
    }


    /**
     * Overridden process. Write all the chunks into SwingWorkerMsgBuffer.
     * Method is declared as final because an observer on the SwingWorkerMsgBuffer
     * should be rather used.
     * @param chunks intermediate results
     */
    @Override
    protected final void process(List<V> chunks) {
        for (V v : chunks) {
            msgBuffer.writeMsg(v);
        }
    }

    public T getAfterDone() {
        if (!isDone())
            throw new RuntimeException("getAfterDone called, but SafeSwingworker was not done!");
        else {
            try {
                return get();
            } catch (Throwable e) {
                throw new RuntimeException("This should not happen!");
            }
        }
    }    

}
