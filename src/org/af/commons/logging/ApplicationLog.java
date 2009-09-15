package org.af.commons.logging;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Observable;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;

//TODO make a "tagging system" when we write specific stuff to the logger and mix it with logger.info
// or logger.debug 

public class ApplicationLog extends Observable {
//    private static final Log logger = LogFactory.getLog(ApplicationLog.class);

//    private static ApplicationLog instance = new ApplicationLog();
    private Vector<LoggingEvent> levents = new Vector<LoggingEvent>();

    public ApplicationLog() {}

//    public static ApplicationLog getInstance() {
//        return instance;
//    }

    public void addEvent(LoggingEvent e) {
        levents.add(e);
        setChanged();
        notifyObservers(e);
    }

    public void reset() {
        levents = new Vector<LoggingEvent>();
        setChanged();
        notifyObservers(null);
    }

    public List<LoggingEvent> getEventsAbove(Level level) {
        List<LoggingEvent> result = new ArrayList<LoggingEvent>();
        for (LoggingEvent le : levents) {
//            String msg = le.getMessage().toString();
//            if (msg.startsWith("R cmd:")) {
//                if (withRCmds)
//                    result.add(le);
//            }
//            else if (msg.startsWith("R Console:")) {
//                if (withRConsole)
//                    result.add(le);
//            }
            if (le.getLevel().isGreaterOrEqual(level))
                result.add(le);
        }
        return result;
    }


    public String getSystemInfo() {
    	String s = "";
    	Properties prop = System.getProperties();
		Enumeration names = prop.propertyNames();
		while (names.hasMoreElements()) {
			String propName = (String) names.nextElement();
			if (!propName.equals("line.separator")) {
				s+=(propName + " : " + System.getProperty(propName)+"\n");
			}
		} 
		return s;
    }
}
