package org.af.commons.logging;


import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.spi.LoggingEvent;

/**
 *
 */
class Log4JToAppLogAppender extends AppenderSkeleton {
    private ApplicationLog applicationLog;

    public Log4JToAppLogAppender(ApplicationLog appLog) {
        this("%-5p - %m%n", appLog);
	}
	
	public Log4JToAppLogAppender(String pattern, ApplicationLog appLog) {
//        this.myLog = myLog;
        this.applicationLog = appLog;
        setLayout(new PatternLayout(pattern));
	}
	
	protected void append(LoggingEvent e) {
		applicationLog.addEvent(e);
//		if(e.getLevel().isGreaterOrEqual(logDialog.level)) {
//			logDialog.append(e);
//		}
	}

	public void close() {
	}

	public boolean requiresLayout() {
		return false;
	}

}
