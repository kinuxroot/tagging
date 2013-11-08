package annotator.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import annotator.util.StringHelper;
import au.com.bytecode.opencsv.CSVReader;

/**
 * @author Brandon B. Lin
 * 
 */
public class Kappa {

	private static ArrayList<String> commonTerms = new ArrayList<>();

	/**
	 * calculate kappa of terms of interest
	 * 
	 * @param file1
	 * @param file2
	 * @return
	 */
	public static double calKappaOfTermOfInterest(File file1, File file2) {
		// empty the common terms each time
		commonTerms.removeAll(commonTerms);
		CSVReader reader1, reader2;
		double kappa = 0;
		try {
			reader1 = new CSVReader(new FileReader(file1));
			reader2 = new CSVReader(new FileReader(file2));
			List<String[]> records1 = reader1.readAll();
			List<String[]> records2 = reader2.readAll();
			reader1.close();
			reader2.close();

			String[] terms1 = clean(records1);
			String[] terms2 = clean(records2);

			kappa = doCal(StringHelper.removeRepeat(terms1),
					StringHelper.removeRepeat(terms2));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return kappa;
	}

	/**
	 * extract terms of interest from csv content
	 * 
	 * @param raw
	 *            : the raw result from csv
	 * @return :the cleaned terms of interest
	 */
	private static String[] clean(List<String[]> raw) {
		raw.remove(0); // remove the header
		for (String[] temp : raw) {
			if (temp[0].equals("")) { // the term of interest is empty
				raw.remove(temp);
			}
		}

		String[] termsOfInterest1 = new String[raw.size()];
		int i = 0;
		for (String[] r : raw) {
			termsOfInterest1[i] = r[0];
			i++;
		}
		return termsOfInterest1;
	}

	/**
	 * do calculation of kappa
	 * 
	 * @param list1
	 *            not empty element
	 * @param list2
	 *            not empty element
	 * @return
	 */
	private static double doCal(String[] list1, String[] list2) {
		int common = 0;
		double kappa = 0;
		Arrays.sort(list1);
		for (int i = 0; i < list2.length; i++) {
			int position = Arrays.binarySearch(list1, list2[i]);
			if (position >= 0) {
				commonTerms.add(list2[position]);
				common++;
			}
		}
		double abYes = common;
		double abNo = list2.length - common;
		double aYesbNo = list1.length - common;
		double aNobYes = list2.length - common;
		double Pr_a = (double) common / (abYes + abNo + aYesbNo + aNobYes);
		double aYes = (aYesbNo + common) / (abYes + abNo + aYesbNo + aNobYes);
		double aNo = 1 - aYes;
		double bYes = (aNobYes + common) / (abYes + abNo + aYesbNo + aNobYes);
		double bNo = 1 - bYes;
		double Pr_e = aYes * bYes + aNo * bNo;
		if (Pr_e == 1) {
			kappa = 1;
		} else {
			kappa = (Pr_a - Pr_e) / (1 - Pr_e);
		}

		return kappa;
	}

	/**
	 * @param file1
	 * @param file2
	 * @return
	 */
	public static double calKappaOfMatchedTerm(File file1, File file2) {
		CSVReader reader1, reader2;
		double kappa = 0;
		try {
			reader1 = new CSVReader(new FileReader(file1));
			reader2 = new CSVReader(new FileReader(file2));
			List<String[]> records1 = reader1.readAll();
			List<String[]> records2 = reader2.readAll();
			String[] fullIds1 = extractFullIds(records1);
			String[] fullIds2 = extractFullIds(records2);
			kappa = doCal(fullIds1, fullIds2);
			reader1.close();
			reader2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return kappa;
	}

	/**
	 * @param records
	 * @return
	 */
	private static String[] extractFullIds(List<String[]> records) {
		records.remove(0); // remove the header
		/* remove the records if their terms not in common */
		ArrayList<String[]> temp = new ArrayList<>();
		for (String[] r : records) {
			if (isCommon(r[0]) == false) {
				temp.add(r);
				//records.remove(r);
			}
		}
		for (String[] s: temp) {
			records.remove(s);
		}

		String[] fullIds = new String[records.size()];
		int i = 0;
		for (String[] r : records) {
			fullIds[i] = r[3];
		}

		return StringHelper.removeRepeat(fullIds);
	}

	/**
	 * @param tar
	 * @return
	 */
	private static boolean isCommon(String tar) {
		for (String s : commonTerms) {
			if (tar.equals(s)) {
				return true;
			}
		}
		return false;
	}

}
