package org.af.commons.errorhandling;

import java.awt.event.ActionListener;

public class RecoverableErrorDialog extends BasicErrorDialog implements ActionListener {



    /**
     * Constructor (no details panel shown, no info on throwable)
     *
     * @param msg displayed error msg (don't pass null)
     */
    protected RecoverableErrorDialog(String msg) {
        super(msg, true);
    }

    /**
     * Constructor (no details panel shown)
     *
     * @param msg displayed error msg (don't pass null)
     * @param e   throwably which caused the error (don't pass null)
     */
    protected RecoverableErrorDialog(String msg, Throwable e) {
        super(msg, e, true);
    }


    /**
     * @return label for the leftmost button: "exit"
     */
    protected String getExitButtonLabel() {return "Exit";}

    /**
     * @return title for the dialog
     */
    protected String getDialogTitle() {
        return 	"Recoverable Error";
    }

    @Override
    protected void onCloseWindow() {
        dispose();
    }
}
