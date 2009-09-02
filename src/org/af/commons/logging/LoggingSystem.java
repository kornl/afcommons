package org.af.commons.logging;

import java.net.URL;

import org.af.commons.logging.widgets.DetailsPanel;
import org.af.commons.logging.widgets.DetailsPanelSimpleLog;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


/**
 * Class encapsulates the whole logging process. This is mainly the log4J setup and the
 * potential handling / redirection of the stdout and stderr streams.
 * But it a also provides an ApplicationLog object which contains the log4J events for
 * convenient access and can be specialised to contain application specific logging info.
 *
 * The class is singelton but can be extended by inheritance. For that reason the only creation
 * of an instance is allowed thru the init method an that only once.
 */
public class LoggingSystem {
    // singleton
    protected static LoggingSystem instance = null;

    //  log4J stuff
    private static String log4JpropsResourceName = null;
    private static Log logger = null;

    // application specific stuff
    protected static ApplicationLog appLog;

    /**
     * Protected Contructor because Singleton
     * @param log4JpropsResourceName Name of log4J properties file. Must be on classpath
     * @param redirectSystemStreams should the stdout/err streams be redirected to the logger?
     * @param printToConsole should the stdout/err streams be printed to the console?
     * @param appLog contains all logging info
     */
    protected LoggingSystem(String log4JpropsResourceName,
                                 boolean redirectSystemStreams,
                                 boolean printToConsole,
                                 ApplicationLog appLog) {
        LoggingSystem.log4JpropsResourceName = log4JpropsResourceName;
        LoggingSystem.appLog = appLog;
        if (redirectSystemStreams)
            redirectSystemStreams(printToConsole);

        configureLog4J();
    }
    
    public static boolean alreadyInitiated() {
    	return instance != null;
    }

    /**
     * Call this to setup the singelton instance at the beginning. Only one call allowed.
     * @param log4JpropsResourceName Name of log4J properties file. Must be on classpath
     * @param redirectSystemStreams should the stdout/err streams be redirected to the logger?
     * @param printToConsole should the stdout/err streams be printed to the console?
     * @param appLog contains all logging info
     * @throws RuntimeException when init was already called before
     */
    public static void init(String log4JpropsResourceName,
                                 boolean redirectSystemStreams,
                                 boolean printToConsole,
                                 ApplicationLog appLog) {
        if (instance != null)
            throw new RuntimeException("Second call to LoggingSystem:init!");
        System.out.println("Configuring LoggingSystem...");
        instance = new LoggingSystem(log4JpropsResourceName,
                redirectSystemStreams, printToConsole, appLog);
        System.out.println("LoggingSystem: instance created.");
    }

    /**
     * @return Singleton instance.
     */
    public static LoggingSystem getInstance() {
        if (instance == null) {
        	init("default-commons-logging.properties",
                false,
                true,
                new ApplicationLog());
        }
        return instance;
    }

    /**
     * Configure log4J thru the properties file
     */
    protected void configureLog4J() {
        System.out.println("Configuring log4J...");
        URL logPropUrl = getClass().getResource(log4JpropsResourceName);
        System.out.println("Configure log4J from props at " + logPropUrl);
        PropertyConfigurator.configure(logPropUrl);
        logger = LogFactory.getLog(LoggingSystem.class);
        logger.info("Configure log4J from props at " + logPropUrl);
        Logger.getRootLogger().addAppender(new Log4JToAppLogAppender(appLog));
        logger.info("Added Log4JToAppLogAppender");
    }

    /**
     * Redirect std out and err streams to the logger.
     * @param printToConsole still print the streams to console?
     */
    protected static void redirectSystemStreams(boolean printToConsole) {
        System.setErr(new SystemPrintStream("ErrStream", printToConsole));
        System.setOut(new SystemPrintStream("OutStream", printToConsole));
    }

    /**
     * @return ApplicationLog object to access logging info
     */
    public ApplicationLog getApplicationLog() {
        return appLog;
    }

    /**
     * @return a JPanel containing the logging infos for a GUI based app
     */
    public DetailsPanel makeDetailsPanel() {
        return new DetailsPanelSimpleLog(getApplicationLog());
    }

}