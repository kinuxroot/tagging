package annotator.util.go;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author Brandon B. Lin
 * 
 */
public class TransObo2Map {
	public static HashMap<String, String> goId2Name = new HashMap<String, String>();

	/**
	 * @param oboPath
	 */
	public static void transferObo2Map(String oboPath) {
		try {
			BufferedReader in = new BufferedReader(new FileReader(oboPath));
			String line;
			while ((line = in.readLine()) != null ) {
				if(line.equals("[Term]")) {
					String id = in.readLine();
					String name = in.readLine();
					goId2Name.put(id, name.substring(6));
				}
				
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		String oboPath = "resource" + File.separator + "gene_ontology_ext.obo";
		transferObo2Map(oboPath);
		System.out.println(goId2Name.size());
		System.out.println(goId2Name.get("id: GO:0005634"));
	}

}
