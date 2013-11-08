package tagger;

import java.io.File;

import tagger.views.WelcomeFrame;
//import annotator.util.go.Prepare4Go;
import annotator.util.go.TransObo2Map;

/**
 * @author Brandon B. Lin
 * 
 *         Program starts from here
 * 
 */
public class Tagger {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// prepare work: transfer .obo to an Hashmap for search
		String oboPath = "resource" + File.separator + "gene_ontology_ext.obo";
		TransObo2Map.transferObo2Map(oboPath);
		//Ontology2IDHelper.
		// download Go annotation files and unzip them
		//String inputFile = "go" + File.separator + "gz" + File.separator +"part1";
		//String inputFile2 = "go" + File.separator + "gz" + File.separator +"part2";
	    //Prepare4Go.unZipAndSaveAsCSV(inputFile, Prepare4Go.PATHTOSAVEGOCSV);
	    //Prepare4Go.unZipAndSaveAsCSV(inputFile2, Prepare4Go.PATHTOSAVEGOCSV);
		new WelcomeFrame();
	}

}
