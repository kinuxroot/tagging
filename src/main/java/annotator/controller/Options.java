/**
 * 
 */
package annotator.controller;

import java.io.File;

/**
 * @author Brandon B. Lin
 * 
 */
public class Options {

	public static String savePath4Search = "results" + File.separator+"search"+File.separator;
	public static String savePath4Recommend = "results" + File.separator+"recommend"+File.separator;
	public static String savePath4Annotation = "results" + File.separator+"annotation"+File.separator;
	public static int sizeOfLargeImage = 600;

	private static String csvFilePath;
	private static String imageFilePath;

	public String getCsvPath() {
		return csvFilePath;
	}

	public void setCsvPath(String csvPath) {
		csvFilePath = csvPath;
	}

	public String getImagePath() {
		return imageFilePath;
	}

	public static void setImagePath(String imagePath) {
		imageFilePath = imagePath;
	}

}
