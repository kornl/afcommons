package org.af.commons.errorhandling;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class handles all throwables / exceptions in a consistent manner.
 * The handler has to be installed on every thread running in the programm.
 * <p/>
 * At the moment all exceptions, their traces and causes are plotted
 * in the main log file. If GUI is present the user is informed
 * about the error by a popup window.
 */

// cannot make this singelton as EDT has to generate an instance by reflection...
public class DefaultExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static final Log logger = LogFactory.getLog(DefaultExceptionHandler.class);

    /**
     * Contructor
     */
    public DefaultExceptionHandler() {
        logger.info("DefaultExceptionHandler constructed.");
    }

    /**
     * Inherited method. Called when an uncaught exception is passed to the handler.
     *
     * @param t Thread were exc. occured
     * @param e Exception
     */

    public void uncaughtException(Thread t, Throwable e) {
        handle(t.toString(), e);
    }

    /**
     * Called by EventDispatchingThread if "sun.awt.exception.handler" is set to
     * this class name and an exception occurs.
     *
     * @param e Exception
     */
    public void handle(Throwable e) {
        handle("EDT", e);
    }

    /**
     * Dump exception trace to log file. Then pop up window
     * to inform user about the error
     *
     * @param threadName Thread were exc. occured
     * @param e Exception
     */
    private void handle(String threadName, Throwable e) {
        String msg = "Unhandled Exception on thread " + threadName + ":" + e;
        if (e.getCause() != null) {
            logger.error("Caused by: " + e.getCause().getMessage(), e.getCause());
        }
        ErrorHandler.getInstance().makeCritErrDialog(msg, e);
    }
}

