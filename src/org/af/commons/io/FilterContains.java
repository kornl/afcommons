package org.af.commons.io;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class FilterContains implements FileFilter {
	private static Log logger = LogFactory.getLog(FilterContains.class);
	
	String[] searchStrings;
	//boolean isDirectory;

	public FilterContains(String[] searchStrings) {
		this.searchStrings = searchStrings;
		//this.isDirectory = isDirectory;
	}

	public boolean accept(File pathname) {
		String filename = pathname.getName().toUpperCase();
		//if (pathname.isDirectory() != isDirectory) return false;
		for (String searchString : searchStrings) {
			if (filename.indexOf(searchString.toUpperCase()) != -1) return true;
			logger.debug("Filename "+filename+" does not contain "+searchString+".");
		}
		return false;
	}

}
