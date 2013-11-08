package tagger.models;


/**
 * @author Brandon B. Lin
 * 
 */
public class Axes {
	public static String[] axes = { "Anatomy", "Disease & Symptoms",
			"Genetics, Proteins & Processes", "Imaging",
			"Medical Intervention", "Pharmaceutical Agent"};
	public static String[] ontologies = { "SNOMEDCT", "SNOMEDCT", "GO",
			"NCIT", "SNOMEDCT", "RXNORM"};
	
	private static Axes instance = null;
	
	
	private Axes() {
		
	}
	
	public static Axes getInstance() {
		if (instance == null) {
			instance = new Axes();
		}
		return instance;
	}

}
