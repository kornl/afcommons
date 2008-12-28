package af.commons.errorhandling;

import java.awt.event.ActionListener;

public class RecoverableErrorDialog extends BasicErrorDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

    /**
     * Constructor (no details panel shown, no info on throwable)
     *
     * @param msg displayed error msg (don't pass null)
     */
    protected RecoverableErrorDialog(String msg) {
        super(msg);
    }

    /**
     * Constructor (no details panel shown)
     *
     * @param msg displayed error msg (don't pass null)
     * @param e   throwably which caused the error (don't pass null)
     */
    protected RecoverableErrorDialog(String msg, Throwable e) {
        super(msg, e);
    }


    /**
     * @return label for the leftmost button: "exit"
     */
    protected String getExitButtonLabel() {return "Ok";}

    /**
     * @return title for the dialog
     */
    protected String getDialogTitle() {
        return 	"Recoverable Error";
    }

}
