/**
 * 
 */
package annotator.util;

import java.util.TreeSet;

/**
 * @author Brandon B. Lin
 * 
 */
public class StringHelper {

	public static String[] removeRepeat(String[] s) {
		TreeSet<String> tr = new TreeSet<String>();
		for (int i = 0; i < s.length; i++) {
			tr.add(s[i]);
		}

		String[] s2 = new String[tr.size()];
		for (int i = 0; i < s2.length; i++) {
			s2[i] = tr.pollFirst();//
		}

		return s2;
	}

	public static String covStringArrayToString(String[] stringArray) {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (String s : stringArray) {
			sb.append(s);
			if (i < stringArray.length - 1) {
				sb.append(",");
			}
			i++;
		}

		return sb.toString();
	}

	private static final int MASK = 0xFFDF; // downcase to upcase

	private static int compare(String o1, String o2) {
		int length1 = o1.length();
		int length2 = o2.length();
		int length = length1 > length2 ? length2 : length1;
		int c1, c2;
		int d1, d2;
		for (int i = 0; i < length; i++) {
			c1 = o1.charAt(i);
			c2 = o2.charAt(i);
			d1 = c1 & MASK;
			d2 = c2 & MASK;
			if (d1 > d2) {
				return 1;
			} else if (d1 < d2) {
				return -1;
			} else {
				if (c1 > c2) {
					return 1;
				} else if (c1 < c2) {
					return -1;
				}
			}
		}
		if (length1 > length2) {
			return 1;
		} else if (length1 < length2) {
			return -1;
		}
		return 0;
	}

	/**
	 * sort string array
	 * @param args
	 * @return
	 */
	public static String[] sortByPOPO(String[] args) {
		String tmp;
		for (int i = 0; i < args.length; i++) {
			for (int j = i + 1; j < args.length; j++) {
				if (compare(args[i], args[j]) > 0) {
					tmp = args[i];
					args[i] = args[j];
					args[j] = tmp;
				}
			}
		}
		return args;
	}
}
