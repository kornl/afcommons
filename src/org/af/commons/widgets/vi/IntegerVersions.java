package org.af.commons.widgets.vi;

import java.util.Comparator;

/**
 * Compares two Strings, which are integer values.
 */
public class IntegerVersions implements Comparator<String> {

	/**
	 * Compares two Strings, which are integer values.
	 */
	public int compare(String o1, String o2) {
		int i1 = Integer.parseInt(o1);
		int i2 = Integer.parseInt(o2);
		return i1-i2;
	}

}
