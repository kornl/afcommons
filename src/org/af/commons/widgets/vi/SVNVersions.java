package org.af.commons.widgets.vi;

import java.util.Comparator;

/**
 * Compares two Strings, which are SVN version numbers.
 */
public class SVNVersions implements Comparator<String> {

	/**
	 * Compares two Strings, which are SVN version numbers.
	 */
	public int compare(String o1, String o2) {
		int i1 = parseInt(o1);
		int i2 = parseInt(o2);
		return i1-i2;
	}
	
	/**
	 * This method returns the integer value from the beginning of a String, for example
	 * parseInt("127M") = 127
	 * parseInt("127") = 127
	 * parseInt("127:128") = 127
	 * @param s String to parse into integer value
	 * @return integer value with which String s starts.
	 */
	public static int parseInt(String s) {
		int version = 0; int i = 0;
		while(i < s.length() && s.charAt(i) >= '0' && s.charAt(i) <= '9') {
			version = 10*version+Integer.parseInt(""+s.charAt(i));
			i++;
		}
		return version;
	}


}
