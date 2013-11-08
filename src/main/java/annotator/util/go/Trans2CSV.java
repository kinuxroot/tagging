package annotator.util.go;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author Brandon B. Lin
 * 
 */
public class Trans2CSV {
	/* split string by whitespace or tab */
	private static final String PATTERN = "(" + (char) 32 + "|" + (char) 9
			+ ")+";

	/**
	 * @param inputFile : file to transfer
	 * @param outputFolder : folder to save csv result
	 */
	public static void transfer2Csv(File inputFile, String outputFolder) {
		ArrayList<String[]> content = new ArrayList<>();
		CSVWriter writer;
		try {
			BufferedReader in = new BufferedReader(new FileReader(inputFile));
			String line;
			// str = in.readLine();
			while ((line = in.readLine()) != null) {
				if (!line.startsWith("!") ) {
					String[] r = line.split(PATTERN);
					if(r.length >= 4) {
						content.add(r);
					}
					
				}
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String name = inputFile.getName() + ".csv";
		File csvFile = new File(outputFolder + File.separator + name);
		try {
			writer = new CSVWriter(new FileWriter(csvFile));
			writer.writeAll(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
