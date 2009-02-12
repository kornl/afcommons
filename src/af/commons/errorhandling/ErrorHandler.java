package af.commons.errorhandling;


import java.awt.Window;

import org.apache.commons.logging.LogFactory;

/**
 * Manager class which handles everything concerning the errorhandling.
 * You can:
 *   - install default exception handlers on the current thread and the EDT
 *   - make GUI dialogs for different error types
 *   - make a GUI dialog to inform about an error / bug and mail logging info 

 * The class is singelton but can be extended by inheritance. For that reason the only creation
 * of an instance is allowed thru the init method an that only once.
 */


public class ErrorHandler {
    // singelton
    protected static ErrorHandler instance;
    // to mail in case of bug
    protected final String developerAddress;



    /**
     * Constructor (protected because singelton)
     * @param developerAddress mail address of the developers, used in inform dialog (don't pass null)
     * @param installDefaultExceptionHandlerOnCurrentThread
     *    install DefaultExceptionHandler on the current thread ?
     * @param installDefaultExceptionHandlerOnEDT
     *    installs DefaultExceptionHandler on the current thread
     */
    protected ErrorHandler(String developerAddress,
                           boolean installDefaultExceptionHandlerOnCurrentThread,
                           boolean installDefaultExceptionHandlerOnEDT) {
        this.developerAddress = developerAddress;
        if (installDefaultExceptionHandlerOnCurrentThread)
            installDefaultExceptionHandlerOnCurrentThread();
        if (installDefaultExceptionHandlerOnEDT)
            installDefaultExceptionHandlerOnEDT();
    }

    /**
     * Initialize the singelton error handler
     *
     * @param developerAddress
     *    mail address of the developers, used in inform dialog (don't pass null)
     * @param installDefaultExceptionHandlerOnCurrentThread
     *    install DefaultExceptionHandler on the current thread ?
     * @param installDefaultExceptionHandlerOnEDT
     *    installs DefaultExceptionHandler on the current thread
      *@throws RuntimeException when developerAddress is null or init was already called before
     */
    public static void init(String developerAddress,
                            boolean installDefaultExceptionHandlerOnCurrentThread,
                            boolean installDefaultExceptionHandlerOnEDT) {
        if (instance != null)
            throw new RuntimeException("Second call to ErrorHandler:init!");
        if (developerAddress == null)
            throw new RuntimeException("Don't pass null for the developer mail address!");
        System.out.println("Initializing ErrorHandler...");
        instance = new ErrorHandler(developerAddress,
                installDefaultExceptionHandlerOnCurrentThread,
                installDefaultExceptionHandlerOnEDT);
    }

    /**
     * @return the singelton instance
     * @throws RuntimeException when not intialized before.
     */
    public static ErrorHandler getInstance() {
        if (instance == null) {
            RuntimeException re = new RuntimeException("Call ErrorHandler:init first!");
            re.printStackTrace();
            LogFactory.getLog(ErrorHandler.class).error("Call ErrorHandler:init first!", re);
            throw re;
        }
        return instance;
    }

    /**
     * install the default exception handler on the current thread
     */
    private void installDefaultExceptionHandlerOnCurrentThread() {
        Thread.currentThread().setUncaughtExceptionHandler(new DefaultExceptionHandler());
    }

    /**
     * install the default exception handler on the EDT 
     */
    private void installDefaultExceptionHandlerOnEDT() {
        System.setProperty("sun.awt.exception.handler", DefaultExceptionHandler.class.getName());
    }

    /**
     * creates a dialog for a criticial non-recoverable error
     * @param msg error message
     * @param e cause of error
     */
    public void makeCritErrDialog(String msg, Throwable e) {
        new CriticalErrorDialog(msg==null?"No message/information available.":msg, e);
    }
    
    /**
     * creates a dialog for a criticial non-recoverable error
     * @param msg error message
     * @param e cause of error
     */
    public void makeCritErrDialog(String msg) {
        new CriticalErrorDialog(msg==null?"No message/information available.":msg);
    }


    /**
     * creates a dialog for a recoverable error
     * @param msg error message
     * @param e cause of error
     */
    public void makeRecovErrDialog(String msg, Throwable e) {
        new RecoverableErrorDialog(msg==null?"No message/information available.":msg, e);
    }

    /**
     * creates a dialog for a recoverable error
     * @param msg error message
     */
    public void makeRecovErrDialog(String msg) {
        new RecoverableErrorDialog(msg==null?"No message/information available.":msg);
    }

    /**
     * creates a dialog for a recoverable error
     * @param owner parent frame
     */
    public void makeInformDialog(Window owner) {
        new InformDialog(owner, developerAddress);
    }
}
