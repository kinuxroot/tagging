package annotator.util;

import java.io.FileReader;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author Brandon B. Lin
 *
 */
public class CSVHelper {
	
	public static ArrayList<String[]> readCSVFile(String csvPath) {
		CSVReader reader;
		ArrayList<String[]> result = null;
		try {		
			reader = new CSVReader(new FileReader(csvPath));
			result = (ArrayList<String[]>) reader.readAll();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return result;
	}

}
