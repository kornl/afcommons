package tests;

import org.af.commons.errorhandling.ErrorHandler;
import org.af.commons.logging.ApplicationLog;
import org.af.commons.logging.LoggingSystem;



public class TestInformDialog {

    public static void main(String[] args) {

        LoggingSystem.init("/commons-logging.properties", false, true, new ApplicationLog());
        // TODO remove
        LoggingSystem.getInstance();
        ErrorHandler.init("bernd_bischl@gmx.net", true, true);
        ErrorHandler.getInstance().makeInformDialog(null);
    }
}

