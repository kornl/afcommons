package af.commons.tests;

import af.commons.errorhandling.ErrorHandler;
import af.commons.logging.ApplicationLog;
import af.commons.logging.LoggingSystem;


public class TestInformDialog {

    public static void main(String[] args) {

        LoggingSystem.init("/commons-logging.properties", false, true, new ApplicationLog());
        // TODO remove
        LoggingSystem.getInstance();
        ErrorHandler.init("bernd_bischl@gmx.net", true, true);
        ErrorHandler.getInstance().makeInformDialog(null);
    }
}

