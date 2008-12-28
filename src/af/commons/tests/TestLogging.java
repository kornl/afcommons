package af.commons.tests;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;

public class TestLogging {
    protected static final Log logger = LogFactory.getLog(TestLogging.class);

    public static void main(String[] args) {
        Logger.getRootLogger().getAllAppenders();
        if (logger.isInfoEnabled()) {
            System.out.println("info is enabled");
            logger.info("bla");
        }
    }
}

