package org.af.commons.tools;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

/**
 * This class provides various static methods for handling String objects.
 */
public class StringTools {
		
	/**
	 * Returns the substring up to the i-th occurrence of split.
	 * Example: getSubstringBefore("test.more.txt", ".", 2) returns "test.more".
	 * If split is not found i-times in s, the whole String s is returned.
	 * @param split Regular expression string to use for splitting.
	 * @return substring up to the i-th occurrence of split.
	 */
	public static String getSubstringBefore(String s, String split, int i) {
		int count = 0;
		for (int k=0;k<s.length();k++) {
			if (s.startsWith(split, k)) {
				count++;
				if (count==i) return s.substring(0, k);
			}
		}
		return s;
	}
	
	/**
	 * Returns the substring from the i-th occurrence of split.
	 * Example: getSubstringBefore("test.more.txt", ".", 2) returns "txt".
	 * If split is not found i-times in s, the empty String "" is returned.
	 * @param split Regular expression string to use for splitting.
	 * @return substring from the the i-th occurrence of split.
	 */
	public static String getSubstringAfter(String s, String split, int i) {
		int count = 0;
		for (int k=0;k<s.length();k++) {
			if (s.startsWith(split, k)) {
				count++;
				if (count==i) return s.substring(k+1);
			}
		}
		return "";
	}
	
	/**
	 * Counts the occurrences of substring in String s.
	 * @param s
	 * @param substring
	 * @return Number of occurrences of substring in String s.
	 */
	public static int count(String s, String substring) {
		int count = 0;
		for (int k=0;k<s.length();k++) {
			if (s.startsWith(substring, k)) {
				count++;			
			}
		}
		return count;
	}
	
	
	/**
	 * Converts an Object array Object[] obj to the String:
	 * "["+obj[0].toString()+", "+obj[1].toString()+","+ ... +", "+obj[obj.length].toString()+"]"
	 * @param strings
	 * @return String that describes the content of the String array
	 */
	public static String arrayToString(Object[] strings) {
		String result = "[";
		for(Object s : strings) {
			result += s+", ";
		}
		if (!result.equals("[")) {
			result = result.substring(0, result.length()-2);
		}
		return result+"]";
	}
	
	/**
	 * Returns the printed stack trace of a Trowable as a String
	 * @param e Trowable whose stack trace should be returned
	 * @return the printed stack trace of a Trowable as a String
	 */
	public static String stackTraceToString(Throwable e) {
	    Writer result = new StringWriter();	    
	    e.printStackTrace(new PrintWriter(result));
	    return result.toString();
	  }
	
	public static String collapseStringArray(String[] ss, String sep) {
        String result = "";
        for (String s : ss) {
            result  += s + sep;
        }
        return result;
    }
    
	public static String collapseStringList(List<String> ss, String sep) {
        String result = "";
        for (String s : ss) {
            result  += s + sep;
        }
        return result;
    }

	public static String collapseStringArray(String[] ss) {
        return collapseStringArray(ss, "\n");
    }

}
