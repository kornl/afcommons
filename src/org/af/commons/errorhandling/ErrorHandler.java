package org.af.commons.errorhandling;


import java.awt.Component;
import java.awt.Window;
import java.lang.reflect.Constructor;

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
    protected final String reportURL;


    /**
     * Constructor (protected because singelton)
     * @param developerAddress mail address of the developers, used in inform dialog (don't pass null)
     * @param installDefaultExceptionHandlerOnCurrentThread
     *    install DefaultExceptionHandler on the current thread ?
     * @param installDefaultExceptionHandlerOnEDT
     *    installs DefaultExceptionHandler on the current thread
     */
    protected ErrorHandler(String developerAddress, String reportURL,
                           boolean installDefaultExceptionHandlerOnCurrentThread,
                           boolean installDefaultExceptionHandlerOnEDT, Class clazz) {
        this.developerAddress = developerAddress;
        this.reportURL = reportURL;
        setInformDialogClass(clazz);
        if (installDefaultExceptionHandlerOnCurrentThread)
            installDefaultExceptionHandlerOnCurrentThread();
        if (installDefaultExceptionHandlerOnEDT)
            installDefaultExceptionHandlerOnEDT();
    }

    /**
     * Initialize the singleton error handler
     *
     * @param developerAddress
     *    mail address of the developers, used in inform dialog (don't pass null)
     * @param installDefaultExceptionHandlerOnCurrentThread
     *    install DefaultExceptionHandler on the current thread ?
     * @param installDefaultExceptionHandlerOnEDT
     *    installs DefaultExceptionHandler on the current thread
     * @param clazz Class of the InformDialog to be called 
     * @throws RuntimeException when developerAddress is null or init was already called before
     */
    public static void init(String developerAddress, String reportURL,
                            boolean installDefaultExceptionHandlerOnCurrentThread,
                            boolean installDefaultExceptionHandlerOnEDT, Class clazz) {
        if (instance != null)
            throw new RuntimeException("Second call to ErrorHandler:init!");
        if (developerAddress == null)
            throw new RuntimeException("Don't pass null for the developer mail address!");
        System.out.println("Initializing ErrorHandler...");
        instance = new ErrorHandler(developerAddress, reportURL,
                installDefaultExceptionHandlerOnCurrentThread,
                installDefaultExceptionHandlerOnEDT, clazz);
    }
    
    /**
     * Initialize the singleton error handler
     *
     * @param developerAddress
     *    mail address of the developers, used in inform dialog (don't pass null)
     * @param installDefaultExceptionHandlerOnCurrentThread
     *    install DefaultExceptionHandler on the current thread ?
     * @param installDefaultExceptionHandlerOnEDT
     *    installs DefaultExceptionHandler on the current thread
      *@throws RuntimeException when developerAddress is null or init was already called before
     */
    public static void init(String developerAddress, String reportURL,
                            boolean installDefaultExceptionHandlerOnCurrentThread,
                            boolean installDefaultExceptionHandlerOnEDT) {
        init(developerAddress, reportURL,
                installDefaultExceptionHandlerOnCurrentThread,
                installDefaultExceptionHandlerOnEDT, ErrorDialog.class);
    }

    /**
     * @return the singleton instance
     * @throws RuntimeException when not initialized before.
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

    public String getDeveloperAddress() {
		return developerAddress;
	}

	public String getReportURL() {
		return reportURL;
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
     * creates a dialog for an error
     * @param msg error message
     * @param e cause of error
     *  @param fatal is the error a fatal error and the application should be shut down
     */
    public void makeErrDialog(String msg, Throwable e, boolean fatal) {
    	Class[] parameterTypes = {String.class, Throwable.class, boolean.class};
    	Constructor con;
		try {
			con = informDialog.getConstructor(parameterTypes);
			con.newInstance(msg, e, fatal);
		} catch (Exception ex) {
			// TODO Oh, what now?
		}    
    }
    
    /**
     * creates a dialog for an error
     * @param msg error message
     * @param e cause of error
     */
    public void makeErrDialog(String msg, Throwable e) {
    	makeErrDialog(msg, e, false);
    }
    
    /**
     * creates a dialog for an error
     * @param msg error message
     */
    public void makeErrDialog(String msg) {
    	makeErrDialog(msg, null);
    }


    protected static Class informDialog;
    
    public static void setInformDialogClass(Class clazz) {
    	informDialog = clazz;
    }
    
    
}
