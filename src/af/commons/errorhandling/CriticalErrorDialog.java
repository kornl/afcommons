package af.commons.errorhandling;

import java.awt.event.ActionListener;

import af.commons.cleanup.CleanUpOnShutDown;


public class CriticalErrorDialog extends BasicErrorDialog implements ActionListener {


    /**
     * Constructor (no details panel shown, no info on throwable)
     * friendly: only called by ErrorHandler.makeCritErrDialog
     *
     * @param msg displayed error msg (don't pass null)
     */
    CriticalErrorDialog(String msg) {
        super(msg);
    }


    /**
     * Constructor (no details panel shown)
     * friendly: only called by ErrorHandler.makeCritErrDialog
     *
     * @param msg displayed error msg (don't pass null)
     * @param e   throwably which caused the error (don't pass null)
     */
    CriticalErrorDialog(String msg, Throwable e) {
        super(msg, e);
    }

    /**
     * handler for exit action
     * close on exit
     */
    protected void onExit() {
        CleanUpOnShutDown.getInstance().cleanUpAll();
    }

    /**
     * @return label for the leftmost button: "exit"
     */
    protected String getExitButtonLabel() {return "Exit";}

    /**
     * @return title for the dialog
     */
    protected String getDialogTitle() {
        return 	"Critical Error";
    }
}
