package af.commons;

public class StringToolbox {
		
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
	 * Just for test purposes...
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Test: "+getSubstringBefore("test.more.txt", ".", 2));
		System.out.println("Test: "+getSubstringAfter("test.more.txt", ".", 2));
		String[] s = {"a","n","aaa"};
		System.out.println("Test: "+arrayToString(s));
	}
	
	public static String arrayToString(String[] strings) {
		String result = "[";
		for(String s : strings) {
			result += s+", ";
		}
		if (!result.equals("[")) {
			result = result.substring(0, result.length()-2);
		}
		return result+"]";
	}
}
